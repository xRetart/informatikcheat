package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.GameOptions
import net.minecraft.util.math.Vec3d
import kotlin.math.cos
import kotlin.math.sin

// Feature, dass dem Spieler das Fliegen ermöglicht
class Fly(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du kannst fliegen."
    val verticalSpeed = 0.5  // vertikale Geschwindigkeit
    val horizontalSpeed = 0.8  // horizontale Geschwindigkeit

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return

        val horizontalVelocity = calculateHorizontalVelocity(player, client.options)
        val verticalVelocity = calculateVerticalVelocity(client.options)

        // setzt Spieler Geschwindigkeiten auf berechnete Geschwindigkeiten
        player.velocity = Vec3d(horizontalVelocity.x, verticalVelocity, horizontalVelocity.z)
    }

    // berechnet vertikale Geschwindigkeit des Spielers
    private fun calculateVerticalVelocity(options: GameOptions) =
        verticalSpeed * if (options.jumpKey.isPressed) {  // nach oben
            1.0
        } else if (options.sneakKey.isPressed) {  // nach unten
            -1.0
        } else {
            .0
        }

    // berechnet horizontale Geschwindigkeit des Spielers
    private fun calculateHorizontalVelocity(player: ClientPlayerEntity, options: GameOptions): Vec3d {
        val forward = yawNormal(player).multiply(horizontalSpeed)  // Bewegungsvektor in Blickrichtung
        val sideward = Vec3d(forward.z, .0, -forward.x)  // Bewegungsvektor senkrecht zur Blickrichtung

        var horizontalVelocity = Vec3d(.0, .0, .0)
        if (options.forwardKey.isPressed) {  // vorwärts
            horizontalVelocity = horizontalVelocity.add(forward)
        }
        if (options.backKey.isPressed) {  // rückwärts
            horizontalVelocity = horizontalVelocity.subtract(forward)
        }
        if (options.leftKey.isPressed) {  // links
            horizontalVelocity = horizontalVelocity.add(sideward)
        }
        if (options.rightKey.isPressed) {  // rechts
            horizontalVelocity = horizontalVelocity.subtract(sideward)
        }

        return horizontalVelocity
    }


    // gibt einen vektor für den horizontalen Teil der Blickrichtung/Kurs (eng. yaw) des Spielers an
    private fun yawNormal(player: ClientPlayerEntity): Vec3d {
        val yaw = player.yaw.toDouble() + 90.0  // Kurs von Fabric ist um 90 Grad versetzt aus unbekannten Gründen
        val yawRadians = Math.toRadians(yaw)  // eingebaute Funktionen funktionieren nur mit Bogenmaß
        val x = cos(yawRadians)
        val y = sin(yawRadians)

        return Vec3d(x, .0, y).normalize()  // normalisierter Vektor
    }
}
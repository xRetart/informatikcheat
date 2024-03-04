package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.util.math.Vec3d
import kotlin.math.cos
import kotlin.math.sin

class Fly(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du kannst fliegen."
    val verticalVelocity = 0.5
    val horizontalVelocity = 0.8

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return


        val forward = yawNormal(player).multiply(horizontalVelocity)
        val sideward = Vec3d(forward.z, .0, -forward.x)

        var horizontalMovement = Vec3d(.0, .0, .0)
        if (client.options.forwardKey.isPressed) {
            horizontalMovement = horizontalMovement.add(forward)
        }
        if (client.options.backKey.isPressed) {
            horizontalMovement = horizontalMovement.subtract(forward)
        }
        if (client.options.leftKey.isPressed) {
            horizontalMovement = horizontalMovement.add(sideward)
        }
        if (client.options.rightKey.isPressed) {
            horizontalMovement = horizontalMovement.subtract(sideward)
        }

        val yVelocity = verticalVelocity * if (client.options.jumpKey.isPressed) {
            1.0
        } else if (client.options.sneakKey.isPressed) {
            -1.0
        } else {
            .0
        }

        player.velocity = Vec3d(horizontalMovement.x, yVelocity, horizontalMovement.z)
    }

    private fun yawNormal(player: ClientPlayerEntity): Vec3d {
        val yawRadians = Math.toRadians(player.yaw.toDouble() + 90.0)
        val x = cos(yawRadians)
        val y = sin(yawRadians)

        return Vec3d(x, .0, y).normalize()
    }
}
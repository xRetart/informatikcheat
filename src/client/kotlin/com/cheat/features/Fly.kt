package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.util.math.Vec3d
import kotlin.math.min

class Fly(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du kannst fliegen."
    val verticalVelocity = 0.5
    val horizontalVelocity = 0.5

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return

        val xVelocity = accelerate(player.velocity.x)
        val zVelocity = accelerate(player.velocity.z)

        if (client.options.jumpKey.isPressed) {
            player.velocity = Vec3d(xVelocity, verticalVelocity, zVelocity)
        } else if (client.options.sneakKey.isPressed) {
            player.velocity = Vec3d(xVelocity, -verticalVelocity, zVelocity)
        } else {
            player.velocity = Vec3d(xVelocity, .0, zVelocity)
        }
    }

    private fun accelerate(velocity: Double): Double {
        return min(velocity * 1.1, horizontalVelocity)
    }
}
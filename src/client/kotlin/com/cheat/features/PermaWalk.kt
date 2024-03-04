package com.cheat.features

import net.minecraft.client.MinecraftClient

class PermaWalk(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du läufst automatisch."
    override fun onTick(client: MinecraftClient) {
        client.options.forwardKey.isPressed = true
    }
}
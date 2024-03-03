package com.cheat.features

import net.minecraft.client.MinecraftClient

class AutoSprint(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du sprintest automatisch."
    override fun onTick(client: MinecraftClient) {
        client.player?.isSprinting = true
    }
}
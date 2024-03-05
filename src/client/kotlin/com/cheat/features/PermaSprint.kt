package com.cheat.features

import net.minecraft.client.MinecraftClient

// Feature, dass beim Laufen automatisch sprintet
class PermaSprint(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du sprintest immer beim Laufen."

    override fun onTick(client: MinecraftClient) {
        client.player?.isSprinting = true
    }
}
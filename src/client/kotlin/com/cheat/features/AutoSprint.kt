package com.cheat.features

import com.cheat.InformatikCheatClient
import com.cheat.menu
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient

class AutoSprint(override var isEnabled: Boolean) : Feature {
    override fun register() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: MinecraftClient ->
            client.player?.setSprinting(isEnabled);
        })
    }
}
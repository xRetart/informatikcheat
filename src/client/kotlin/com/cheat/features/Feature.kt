package com.cheat.features

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient

interface Feature {
    val description: String
    var isEnabled: Boolean

    fun toggle() {
        isEnabled = !isEnabled
    }

    fun onTick(client: MinecraftClient)

    fun register() {
        val callback = { client: MinecraftClient ->
            if (isEnabled) {
                onTick(client)
            }
        }

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick(callback))
    }
}
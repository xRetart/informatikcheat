package com.cheat.features

import net.minecraft.client.MinecraftClient

// Feature, dass durchgehend für den Spieler läuft
class PermaWalk(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du läufst automatisch."

    override fun onTick(client: MinecraftClient) {
        // simuliert konstantes drücken <W> Taste, bloß dass es sich nicht durch z.B. den Chat unterbrechen lässt
        client.options.forwardKey.isPressed = true
    }
}
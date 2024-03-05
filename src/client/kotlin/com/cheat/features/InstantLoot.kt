package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.slot.SlotActionType

// Feature, dass beim Öffnen einer Truhe dem Spieler sofort alle Gegenstände ins Inventar überträgt
class InstantLoot(override var isEnabled: Boolean) : Feature {
    override val description = "Du nimmst automatisch alle Gegenstände aus Kisten, die du öffnest."

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return

        if (player.currentScreenHandler is GenericContainerScreenHandler) {  // Guckt der Spieler in eine Truhe?
            val chest = player.currentScreenHandler as GenericContainerScreenHandler

            for (i in 0 until chest.inventory.size()) {  // geht jeden Slot der Truhe durch
                val slot = chest.getSlot(i)
                if (slot.hasStack()) {  // Sind Gegenstände in diesem Slot?
                    // überträgt den Gegenstand auf das Inventar des Spielers
                    interactionManager.clickSlot(
                        player.currentScreenHandler.syncId,
                        i,
                        0,
                        SlotActionType.QUICK_MOVE,
                        player
                    )
                }
            }
            player.closeScreen()  // schließt Truhe
        }
    }
}
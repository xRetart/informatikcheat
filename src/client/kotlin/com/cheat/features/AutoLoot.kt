package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType

class AutoLoot(override var isEnabled: Boolean) : Feature {
    override val description = "Du nimmst automatisch alle Gegenstände aus Kisten, die du öffnest."

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return

        if (player.currentScreenHandler is GenericContainerScreenHandler) {
            val chest = player.currentScreenHandler as GenericContainerScreenHandler

            for (i in 0 until chest.inventory.size()) {
                val slot: Slot = chest.getSlot(i)
                if (slot.hasStack()) {
                    interactionManager.clickSlot(
                        player.currentScreenHandler.syncId,
                        i,
                        0,
                        SlotActionType.QUICK_MOVE,
                        player
                    )
                }
            }
            player.closeScreen()
        }
    }
}
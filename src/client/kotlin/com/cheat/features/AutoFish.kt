package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.item.FishingRodItem
import net.minecraft.util.Hand

class AutoFish(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du fängst automatisch Fische."
    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return

        if (player.inventory.mainHandStack.item is FishingRodItem) {
            if (player.fishHook == null) {
                interactionManager.interactItem(player, Hand.MAIN_HAND)
            } else {
                player.fishHook!!.dataTracker.changedEntries?.forEach { event ->
                    if (event.id == 9) {
                        interactionManager.interactItem(player, Hand.MAIN_HAND)
                    }
                }
            }
        }
    }
}
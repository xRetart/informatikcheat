package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.item.FishingRodItem
import net.minecraft.util.Hand

// Feature, dass die Angel automatisch auswirft und wieder einholt
class AutoFish(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du fängst automatisch Fische."

    override fun onTick(client: MinecraftClient) {
        // sicherstellen, dass es einen Spieler und Manager für Interaktionen gibt
        val player = client.player ?: return
        val interactionManager = client.interactionManager ?: return

        if (player.inventory.mainHandStack.item is FishingRodItem) {  // Hat der Spieler eine Angel in der Hand?
            if (player.fishHook == null) {  // Angel ist noch nicht ausgeworfen
                interactionManager.interactItem(player, Hand.MAIN_HAND)  // wirft angel auf
            } else {  // Angel ist bereits ausgeworfen
                // gibt Funktion an, wenn es ein Ereignis am Köder gibt
                player.fishHook!!.dataTracker.changedEntries?.forEach { event ->
                    if (event.id == 9) {  // ein Fisch ist am Köder
                        interactionManager.interactItem(player, Hand.MAIN_HAND)  // Angel wird wieder eingeholt
                    }
                }
            }
        }
    }
}
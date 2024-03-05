package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.GameOptions
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.Items

// Feature, dass im Fall von Hunger automatisch isst
class AutoEat(override var isEnabled: Boolean) : Feature {
    private val threshold = 7  // Schwellenwert, ab dem automatisch gegessen wird
    override val description = "Du isst automatisch, wenn du $threshold Hungerpunkte oder weniger hast."

    private var previouslySelectedSlot: Int? = null
    private var eating = false

    override fun onTick(client: MinecraftClient) {
        // stellt sicher, dass es einen Spieler gibt
        val player = client.player ?: return

        val hungry = isHungry(player)
        if (hungry && !eating) {  // Sollte gegessen werden?
            startEating(player, client.options)
        } else if (!hungry && eating) {  // Sollten aufgehört werden mit Essen?
            stopEating(player, client.options)
        }
    }

    private fun startEating(player: ClientPlayerEntity, options: GameOptions) {
        previouslySelectedSlot = player.inventory.selectedSlot  // speicher ausgewählten Slot

        // versuche jeden Gegenstand in der Hotbar zu essen, bis eins gefunden wurde
        var slot = 0
        while (!eating && slot <= 8) {
            tryEat(player, slot, options)
            ++slot
        }
    }

    // stellt Zustand vor dem Essen wieder her
    private fun stopEating(player: ClientPlayerEntity, options: GameOptions) {
        eating = false
        options.useKey.isPressed = false  // hört auf Gegenstand in der Hand zu essen
        player.inventory.selectedSlot = previouslySelectedSlot!!
    }

    // versucht den Gegenstand in `slot` zu essen
    private fun tryEat(player: ClientPlayerEntity, slot: Int, options: GameOptions) {
        val itemStack = player.inventory.getStack(slot)

        if (isEdible(itemStack.item)) {  // Ist der Gegenstand essbar?
            eating = true
            player.inventory.selectedSlot = slot
            options.useKey.isPressed = true  // fange an zu essen
        }
    }

    // Ist die Hungergrenze unterschritten?
    private fun isHungry(player: PlayerEntity): Boolean {
        return player.hungerManager.foodLevel <= threshold
    }

    // Ist der Gegenstand essbar?
    private fun isEdible(item: Item): Boolean {
        // Manche Nahrungsmittel sind gifting und zählen hier nicht als essbar (z.B. verrottetes Fleisch)
        return item.isFood && when (item) {
            Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.CHORUS_FRUIT -> false
            else -> true
        }
    }
}
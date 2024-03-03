package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.GameOptions
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.Items

class AutoEat(override var isEnabled: Boolean) : Feature {
    private val threshold = 7
    override val description = "Du isst automatisch, wenn du $threshold Hungerpunkte oder weniger hast."

    private var previouslySelectedSlot: Int? = null
    private var eating = false

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return

        val hungry = isHungry(player)
        if (hungry && !eating) {
            startEating(player, client.options)
        } else if (!hungry && eating) {
            stopEating(player, client.options)
        }
    }

    private fun startEating(player: ClientPlayerEntity, options: GameOptions) {
        previouslySelectedSlot = player.inventory.selectedSlot

        var slot = 0
        while (!eating && slot <= 8) {
            tryEat(player, slot, options)
            ++slot
        }
    }

    private fun stopEating(player: ClientPlayerEntity, options: GameOptions) {
        eating = false
        options.useKey.isPressed = false
        player.inventory.selectedSlot = previouslySelectedSlot!!
    }

    private fun tryEat(player: ClientPlayerEntity, slot: Int, options: GameOptions) {
        val itemStack = player.inventory.getStack(slot)

        if (isEdible(itemStack.item)) {
            eating = true
            player.inventory.selectedSlot = slot
            options.useKey.isPressed = true
        }
    }

    private fun isHungry(player: PlayerEntity): Boolean {
        return player.hungerManager.foodLevel <= threshold
    }

    private fun isEdible(item: Item): Boolean {
        return item.isFood && when (item) {
            Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.CHORUS_FRUIT -> false
            else -> true
        }
    }
}
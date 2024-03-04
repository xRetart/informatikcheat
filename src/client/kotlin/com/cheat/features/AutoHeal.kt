package com.cheat.features

import com.cheat.utility.ItemID
import com.cheat.utility.findInHotbar
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.GameOptions
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity

class AutoHeal(override var isEnabled: Boolean) : Feature {
    private val threshold = 8
    override val description =
        "Du heilst dich automatisch mit einem Goldapfel in deiner Hotbar, wenn du ${threshold.toFloat() / 2.0} Herzen oder weniger hast."

    private var previouslySelectedSlot = -1
    private var healing = false

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return

        val regenerating = player.hasStatusEffect(StatusEffects.REGENERATION)
        if (healing && regenerating) {
            player.inventory.selectedSlot = previouslySelectedSlot
            client.options.useKey.isPressed = false
            healing = false
        } else if (!healing && isLow(player) && !regenerating) {
            heal(player, client.options)
        }
    }

    private fun heal(player: ClientPlayerEntity, options: GameOptions) {
        findInHotbar(player.inventory, ItemID.GOLDEN_APPLE)?.let { slot ->
            eat(player, options, slot)
        }
    }

    private fun eat(player: ClientPlayerEntity, options: GameOptions, slot: Int) {
        previouslySelectedSlot = player.inventory.selectedSlot
        player.inventory.selectedSlot = slot
        options.useKey.isPressed = true
        healing = true
    }

    private fun isLow(player: PlayerEntity): Boolean {
        return player.health <= threshold
    }
}
package com.cheat.features

import com.cheat.utility.ItemID
import com.cheat.utility.findInHotbar
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.GameOptions
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity

// Feature, dass bei niedrigen Lebenspunkten automatisch einen goldenen Apfel zur Heilung isst
class AutoHeal(override var isEnabled: Boolean) : Feature {
    private val threshold = 8  // Schwellenwert für Lebenspunkte
    override val description =
        "Du heilst dich automatisch mit einem Goldapfel in deiner Hotbar, wenn du ${threshold.toFloat() / 2.0} Herzen oder weniger hast."

    private var previouslySelectedSlot = -1
    private var healing = false

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return

        val regenerating = player.hasStatusEffect(StatusEffects.REGENERATION)  // Regeneriert der Spieler bereits?
        if (healing && regenerating) {  // Spieler regeneriert bereits, aber isst immer noch (Verschwendung)
            stopHealing(player, client)
        } else if (!healing && isLow(player) && !regenerating) {  // Spieler sollte goldenen Apfel essen
            startHealing(player, client.options)
        }
    }

    // stoppt Essen vom goldenen Apfel und stellt vorherigen Zustand wieder her
    private fun stopHealing(player: ClientPlayerEntity, client: MinecraftClient) {
        player.inventory.selectedSlot = previouslySelectedSlot
        client.options.useKey.isPressed = false
        healing = false
    }

    // fängt an mit dem Essen eines goldenen Apfels
    private fun startHealing(player: ClientPlayerEntity, options: GameOptions) {
        findInHotbar(player.inventory, ItemID.GOLDEN_APPLE)?.let { slot ->
            eat(player, options, slot)
        }
    }

    // ist den Gegenstand bei `slot` in der Hotbar
    private fun eat(player: ClientPlayerEntity, options: GameOptions, slot: Int) {
        previouslySelectedSlot = player.inventory.selectedSlot
        player.inventory.selectedSlot = slot
        options.useKey.isPressed = true
        healing = true
    }

    // Ist die Lebensgrenze unterschritten?
    private fun isLow(player: PlayerEntity): Boolean {
        return player.health <= threshold
    }
}
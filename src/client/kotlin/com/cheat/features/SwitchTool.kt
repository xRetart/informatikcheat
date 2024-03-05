package com.cheat.features

import com.cheat.utility.findInHotbarBy
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.item.SwordItem
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult

// Feature, dass für den Spieler automatisch das zum anvisierten Block/Entität passende Werkzeug/Schwert auswählt
class SwitchTool(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du wechselst automatisch auf das passende Werkzeug/Schwert in deiner Hotbar."

    override fun onTick(client: MinecraftClient) {
        val world = client.world ?: return
        val player = client.player ?: return

        val crosshairTarget = client.crosshairTarget
        if (crosshairTarget is BlockHitResult) {  // Guckt der Spieler auf einen Block?
            val blockHitResult = crosshairTarget
            val blockState = world.getBlockState(blockHitResult.blockPos)

            if (!blockState.isAir) {  // Ist der etwas außer Luft?
                // rüstet optimales Werkzeug aus
                findOptimalToolSlot(player, blockState)?.let { slot ->
                    player.inventory.selectedSlot = slot
                }
            }
        } else if (crosshairTarget is EntityHitResult) {  // Guckt der Spieler auf eine Entität?
            // rüstet bestes schwert aus
            findStrongestSword(player)?.let { slot ->
                player.inventory.selectedSlot = slot
            }
        }
    }

    // gibt die Slotnummer des Werkzeuges an, welches sich am besten für den Block eignet
    private fun findOptimalToolSlot(player: ClientPlayerEntity, blockState: BlockState?): Int? {
        return findInHotbarBy(player.inventory) { stack -> stack.getMiningSpeedMultiplier(blockState) }
    }

    // gibt die Slotnummer des Schwertes mit dem meisten Schaden an
    private fun findStrongestSword(player: ClientPlayerEntity): Int? {
        return findInHotbarBy(player.inventory) { stack ->
            if (stack.item is SwordItem) {  // Ist der Gegenstand ein Schwert?
                (stack.item as SwordItem).attackDamage
            } else {
                0.5f  // default Schaden = 1/2 Herz
            }
        }
    }
}
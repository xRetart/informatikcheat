package com.cheat.features

import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult

class SwitchTool(override var isEnabled: Boolean) : Feature {
    override val description: String = "Du wechselst automatisch auf das passende Werkzeug/Schwert in deiner Hotbar."

    override fun onTick(client: MinecraftClient) {
        val world = client.world ?: return
        val player = client.player ?: return

        val crosshairTarget = client.crosshairTarget
        if (crosshairTarget is BlockHitResult) {
            val blockHitResult = crosshairTarget
            val blockState = world.getBlockState(blockHitResult.blockPos)

            if (!blockState.isAir) {
                findOptimalToolSlot(player, blockState)?.let { slot ->
                    player.inventory.selectedSlot = slot
                }
            }
        } else if (crosshairTarget is EntityHitResult) {
            findStrongestSword(player)?.let { slot ->
                player.inventory.selectedSlot = slot
            }
        }
    }

    private fun findOptimalToolSlot(player: ClientPlayerEntity, blockState: BlockState?): Int? {
        return findMaxBy(player.inventory) { stack -> stack.getMiningSpeedMultiplier(blockState) }
    }

    private fun findStrongestSword(player: ClientPlayerEntity): Int? {
        return findMaxBy(player.inventory) { stack ->
            if (stack.item is SwordItem) {
                (stack.item as SwordItem).attackDamage
            } else {
                0.5f
            }
        }
    }

    private fun findMaxBy(inventory: PlayerInventory, criterion: (ItemStack) -> Float): Int? {
        val slots = (0..8)
        val stacks = slots.map { slot -> slot to inventory.getStack(slot) }
        val nonEmptyStacks = stacks.filter { (slot, stack) -> stack != ItemStack.EMPTY }
        val criteria = nonEmptyStacks.map { (slot, stack) -> slot to criterion(stack) }

        val optimalToolSlot = criteria.maxByOrNull { (slot, criteria) -> criteria }?.first

        return optimalToolSlot
    }
}
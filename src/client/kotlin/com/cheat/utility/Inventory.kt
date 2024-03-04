package com.cheat.utility

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

fun findInHotbar(inventory: PlayerInventory, itemId: ItemID): Int? {
    val stack = ItemStack(Item.byRawId(itemId.id))
    val slot = inventory.getSlotWithStack(stack)

    return if (slot in 0..8) slot else null
}
package com.cheat.utility

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

// gibt die Slotnummer des ersten Gegenstandes in der Hotbar mit der gegebenen ID wieder
// Ist kein passender Gegenstand vorhanden, wird null zurückgegeben.
fun findInHotbar(inventory: PlayerInventory, itemId: ItemID): Int? {
    val stack = ItemStack(Item.byRawId(itemId.id))
    val slot = inventory.getSlotWithStack(stack)  // finde Slotnummer

    return if (slot in 0..8) slot else null
}

// gibt die Slotnummer eines Gegenstandes aus, welches den größten Wert eines Kriteriums hat
fun findInHotbarBy(inventory: PlayerInventory, criterion: (ItemStack) -> Float): Int? {
    val slots = (0..8)  // alle Slotnummern in der Hotbar
    val stacks = slots.map { slot -> slot to inventory.getStack(slot) }  // Slotnummer -> Gegenstand
    val nonEmptyStacks = stacks.filter { (slot, stack) -> stack != ItemStack.EMPTY }  // filtert leere Slots
    val criteria = nonEmptyStacks.map { (slot, stack) -> slot to criterion(stack) }  // ordnet Slots Kriterien zu

    return criteria.maxByOrNull { (slot, criteria) -> criteria }?.first  // findet Maximum
}
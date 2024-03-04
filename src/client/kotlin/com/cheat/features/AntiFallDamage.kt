package com.cheat.features

import com.cheat.utility.ItemID
import com.cheat.utility.findInHotbar
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.ClientPlayerInteractionManager
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.Hand
import net.minecraft.util.math.Vec3d


class AntiFallDamage(override var isEnabled: Boolean) : Feature {
    override val description: String = "Mit einem Eimer Wasser in deiner Hotbar, bekommst du keinen Fallschaden mehr."

    private var previouslySelected: Int? = null
    private var previousPitch: Float? = null

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val world = client.world ?: return
        val interactionManager = client.interactionManager ?: return

        if (previouslySelected == null) {
            preventFallDamage(player, world, interactionManager)
        } else {
            cleanup(player, interactionManager)
        }

    }

    private fun cleanup(player: ClientPlayerEntity, interactionManager: ClientPlayerInteractionManager) {
        player.pitch = 90f
        interactionManager.interactItem(player, Hand.MAIN_HAND)
        player.swingHand(Hand.MAIN_HAND)
        player.inventory.selectedSlot = previouslySelected!!
        previouslySelected = null

        player.pitch = previousPitch!!
        previousPitch = null
    }

    private fun preventFallDamage(
        player: ClientPlayerEntity,
        world: ClientWorld,
        interactionManager: ClientPlayerInteractionManager
    ) {
        val bucketSlot = findInHotbar(player.inventory, ItemID.WATER_BUCKET) ?: return

        val groundDistance = groundDistance(player, world)
        if (player.fallDistance >= player.safeFallDistance && groundDistance < 2) {
            previouslySelected = player.inventory.selectedSlot
            player.inventory.selectedSlot = bucketSlot

            previousPitch = player.pitch
            player.pitch = 90f
            interactionManager.interactItem(player, Hand.MAIN_HAND)
            player.swingHand(Hand.MAIN_HAND)

            player.velocity = Vec3d(.0, player.velocity.y, .0)
        }
    }

    private fun groundDistance(player: ClientPlayerEntity, world: ClientWorld): Int {
        val playerPos = player.pos

        var blockBelow = player.blockPos
        while (world.getBlockState(blockBelow).isAir) {
            blockBelow = blockBelow.down()
        }

        return (playerPos.y - blockBelow.y).toInt()

    }
}
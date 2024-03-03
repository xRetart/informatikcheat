package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity

class KillAura(override var isEnabled: Boolean) : Feature {
    override val description = "Greife alles in deiner Reichweite automatisch an."

    private val respectCooldown = false

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val world = client.world ?: return
        val interactionManager = client.interactionManager ?: return

        val cooldown = player.getAttackCooldownProgress(0f)
        if (cooldown == 1f || !respectCooldown) {
            findTarget(world, player)?.let { target ->
                interactionManager.attackEntity(player, target)
            }
        }

    }

    private fun findTarget(world: ClientWorld, player: ClientPlayerEntity): Entity? {
        val candidates = world.entities?.filter { entity ->
            entity.isAttackable
                    && !entity.isPlayer
                    && entity.isLiving
                    && entity.isInRange(player, 4.0)
        }
        val closest = candidates?.minByOrNull { entity -> player.distanceTo(entity) }

        return closest
    }
}
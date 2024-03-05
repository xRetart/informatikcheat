package com.cheat.features

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity

// Feature, dass automatisch alles in der Reichweite des Spielers angreift
class KillAura(override var isEnabled: Boolean) : Feature {
    override val description = "Greife alles in deiner Reichweite automatisch an."

    // Waffen haben eine Abklingzeit.
    // Wird diese eingehalten, macht die Waffe deutlich mehr schaden.
    // Soll die Abklingzeit respektiert werden?
    private val respectCooldown = false

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val world = client.world ?: return
        val interactionManager = client.interactionManager ?: return

        val cooldown = player.getAttackCooldownProgress(0f)
        if (cooldown == 1f || !respectCooldown) {  // Ist die Abklingzeit abgelaufen (sofern sie respektiert wird)?
            findTarget(world, player)?.let { target ->
                interactionManager.attackEntity(player, target)  // greife die Entität an
            }
        }

    }

    // findet die nächste angreifbare Entität
    private fun findTarget(world: ClientWorld, player: ClientPlayerEntity): Entity? {
        val candidates = world.entities?.filter { entity ->
            entity.isAttackable  // muss attackierbar sein
                    && !entity.isPlayer  // darf kein anderer Spieler sein
                    && entity.isLiving  // muss Lebewesen sein
                    && entity.isInRange(player, 4.0)  // muss in Spielers Reichweite sein
        }
        // entscheidet sich für Entität, die am nächsten zum Spieler ist
        val closest = candidates?.minByOrNull { entity -> player.distanceTo(entity) }

        return closest
    }
}
package com.cheat.features

import com.cheat.utility.ItemID
import com.cheat.utility.findInHotbar
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.ClientPlayerInteractionManager
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.Hand
import net.minecraft.util.math.Vec3d


// Feature, das mithilfe eines Wassereimers automatisch jeden Fallschaden umgeht
class AntiFallDamage(override var isEnabled: Boolean) : Feature {
    override val description: String = "Mit einem Eimer Wasser in deiner Hotbar, bekommst du keinen Fallschaden mehr."

    private var previouslySelected: Int? = null  // Speicher für den vor der Durchführung ausgewählter Slot der Hotbar
    private var previousPitch: Float? = null  // Speicher für die Blickhöhe vor der Durchführung

    override fun onTick(client: MinecraftClient) {
        // sicherstellen, dass es einen Spieler, eine Welt und einen Manager für Interaktionen gibt
        val player = client.player ?: return
        val world = client.world ?: return
        val interactionManager = client.interactionManager ?: return

        // Die Durchführung passiert in 2 Schritten.
        // 1. Setzen des Wassereimers
        // 2. Aufnehmen des Wassers und Wiederherstellung des vorherigen Zustandes
        if (previouslySelected == null) {  // Ist der Speicher für den vorher ausgewählten Slot belegt? Wenn nicht, müssen wir im ersten Schritt sein.
            preventFallDamage(player, world, interactionManager)
        } else {  // Wir müssen im 2. Schritt sein.
            cleanup(player, interactionManager)
        }

    }

    // stellt Zustand vor der Durchführung wieder her
    private fun cleanup(player: ClientPlayerEntity, interactionManager: ClientPlayerInteractionManager) {
        interactBelow(player, interactionManager)  // sammelt das platzierte Wasser wieder auf
        player.inventory.selectedSlot = previouslySelected!!  // wählt vorher gespeicherten Slot in der Hotbar aus
        previouslySelected = null

        player.pitch = previousPitch!!  // stellt vorher gespeicherte Blickhöhe wieder her
        previousPitch = null
    }

    private fun preventFallDamage(
        player: ClientPlayerEntity,
        world: ClientWorld,
        interactionManager: ClientPlayerInteractionManager
    ) {
        // findet Slotnummer für Wassereimer in der Hotbar
        val bucketSlot = findInHotbar(player.inventory, ItemID.WATER_BUCKET) ?: return

        val groundDistance = groundDistance(player, world)
        if (player.fallDistance >= player.safeFallDistance && groundDistance < 2) {  // Fällt und landet der Spieler gleich?
            previouslySelected = player.inventory.selectedSlot  // speichert ausgewählten Slot
            player.inventory.selectedSlot = bucketSlot

            previousPitch = player.pitch  // speichert Blickhöhe
            interactBelow(player, interactionManager)  // platziert Wasser

            // stoppt horizontale Bewegung des Spielers (kann Landung verfälschen)
            player.velocity = Vec3d(.0, player.velocity.y, .0)
        }
    }

    // interagiert mit dem Gegenstand in der Hand mit dem Block unter dem Spieler
    private fun interactBelow(player: ClientPlayerEntity, interactionManager: ClientPlayerInteractionManager) {
        player.pitch = 90f  // setzt Blick des Spielers gerade nach unten

        // simuliert Rechtsklick mit dem Gegenstand in der Hand
        interactionManager.interactItem(player, Hand.MAIN_HAND)
        player.swingHand(Hand.MAIN_HAND)
    }

    // berechnet die Distanz zwischen Spieler und dem nächsten Block unter ihm/ihr
    private fun groundDistance(player: ClientPlayerEntity, world: ClientWorld): Int {
        // steigt Blöcke unter dem Spieler ab, bis der Block nicht mehr Luft ist
        var blockBelow = player.blockPos
        while (world.getBlockState(blockBelow).isAir) {
            blockBelow = blockBelow.down()
        }

        return (player.pos.y - blockBelow.y).toInt()

    }
}
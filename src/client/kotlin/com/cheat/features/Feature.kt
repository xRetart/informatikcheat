package com.cheat.features

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient

// allgemeines Interface für jedes Feature
interface Feature {
    val description: String  // kurze Beschreibung der Funktion
    var isEnabled: Boolean  // Ist das Feature aktuell aktiviert?

    // Die Funktion schaltet das Feature um.
    // Das bedeutet, wenn es noch nicht aktiviert ist, wird es aktiviert
    // und, wenn es schon aktiviert ist, wird es deaktiviert.
    fun toggle() {
        isEnabled = !isEnabled
    }

    // eine abstrakte Methode, die nach jedem Tick abgerufen wird, wenn das Feature aktiviert ist
    fun onTick(client: MinecraftClient)

    // registriert das Feature bei der internen Event-API
    fun register() {
        val callback = { client: MinecraftClient ->
            // Wenn das Feature aktiviert ist, wird `onTick` aufgerufen
            if (isEnabled) {
                onTick(client)
            }
        }

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick(callback))
    }
}
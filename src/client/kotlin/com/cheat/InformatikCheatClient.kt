package com.cheat

import com.cheat.features.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW


// eine HashMap die den Namen eines Features einer globalen (an den Client gebundenen) Instanz des Features zuordnet
val features: MutableMap<String, Feature> = mutableMapOf(
    "PermaSprint" to PermaSprint(false),
    "PermaWalk" to PermaWalk(false),
    "AutoEat" to AutoEat(false),
    "AutoHeal" to AutoHeal(false),
    "AutoFish" to AutoFish(false),
    "SwitchTool" to SwitchTool(false),
    "InstantLoot" to InstantLoot(false),
    "KillAura" to KillAura(false),
    "AntiFallDamage" to AntiFallDamage(false),
    "Fly" to Fly(false),
)

// Hauptklasse, die Funktion für den Einstiegspunkt (`onInitializeClient`)
// und an den Client gebundene Daten (Tastenbelegung für das Menu) beinhaltet
object InformatikCheatClient : ClientModInitializer {
    // Tastenbelegung um das Cheat Menu zu Öffnen
    private val menuKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(
            "Öffne das Cheat Menu",  // Beschreibung
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_U,  // Default Wert = U
            "category.cheat.menu",  // interne Kategorie ID
        )
    )

    // Funktion, die direkt nach dem Start des Spiels ausgeführt wird (der Einstiegspunkt)
    override fun onInitializeClient() {
        registerMenu()
        registerFeatures()
    }

    // registriert bei der API, die Tastenbelegung für das Menu
    private fun registerMenu() {
        // Nach jedem Tick (kleinste Zeiteinheit im Spiel) wird die gegebene Funktion ausgeführt
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: MinecraftClient ->
            if (menuKey.wasPressed()) {  // Wurde die Taste, auf die das Menu belegt wurde, gedrückt?
                client.setScreen(MenuScreen())  // Setzte das aktuelle Menu (Screen) auf das Menu (durch MenuScreen definiert)
            }
        })
    }

    // registriert jedes einzelne in der variable `features` definierte Feature
    private fun registerFeatures() {
        for (feature in features) {
            feature.value.register()
        }
    }
}
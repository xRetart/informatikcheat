package com.cheat

import com.cheat.features.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW


fun menu(client: MinecraftClient): Int {
    client.setScreen(MenuScreen())
    return 1
}

val features: MutableMap<String, Feature> = mutableMapOf(
    "AutoSprint" to AutoSprint(false),
    "AutoEat" to AutoEat(false),
    "AutoHeal" to AutoHeal(false),
    "AutoLoot" to AutoLoot(false),
    "AutoFish" to AutoFish(false),
    "KillAura" to KillAura(false),
    "AntiFallDamage" to AntiFallDamage(false),
    "Fly" to Fly(false),
)

object InformatikCheatClient : ClientModInitializer {
    private val menuKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(
            "Open Cheat Menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_U,
            "category.cheat.test",
        )
    )

    override fun onInitializeClient() {
        registerEvents()
        initializeFeatures()
    }

    private fun registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: MinecraftClient ->
            if (menuKey.wasPressed()) {
                menu(client)
            }
        })
    }

    private fun initializeFeatures() {
        for (feature in features) {
            feature.value.register()
        }
    }
}
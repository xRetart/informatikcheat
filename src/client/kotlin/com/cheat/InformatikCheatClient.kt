package com.cheat

import com.cheat.features.AutoSprint
import com.cheat.features.Feature
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.server.command.CommandManager.*
import net.minecraft.server.command.ServerCommandSource
import org.lwjgl.glfw.GLFW


fun menu(client: MinecraftClient): Int {
	client.setScreen(TutorialScreen())
	return 1
}

val features: MutableMap<String, Feature> = mutableMapOf(
	"autosprint" to AutoSprint(true),
)
object InformatikCheatClient : ClientModInitializer {
	private val commands: Map<String, (CommandContext<ServerCommandSource>) -> Int> = mapOf(
		"autosprint" to { context -> enabledCheats.toggle(CheatKind.AUTO_SPRINT, context) },
		"autoeat" to { context -> enabledCheats.toggle(CheatKind.AUTO_EAT, context) },
		"autosoup" to { context -> enabledCheats.toggle(CheatKind.AUTO_SOUP, context) },
		"autopotion" to { context -> enabledCheats.toggle(CheatKind.AUTO_POTION, context) },
		"killaura" to { context -> enabledCheats.toggle(CheatKind.KILL_AURA, context) },
		"antiafk" to { context -> enabledCheats.toggle(CheatKind.ANTI_AFK, context) },
		"xray" to { context -> enabledCheats.toggle(CheatKind.XRAY, context) },
		"noclip" to { context -> enabledCheats.toggle(CheatKind.NO_CLIP, context) },
		"listCheats" to { context -> enabledCheats.list(context) }
	)
	private val keyBinding: KeyBinding = KeyBindingHelper.registerKeyBinding(
		KeyBinding(
			"Open Cheat Menu",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_U,
			"category.cheat.test",
		)
	)

	override fun onInitializeClient() {
		initializeFeatures()
		registerCommands()
		registerEvents()
	}

	private fun registerEvents() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: MinecraftClient ->
			while (keyBinding.wasPressed()) {
				menu(client)
			}
		})
	}

	private fun initializeFeatures() {
		for (feature in features) {
			feature.value.register()
		}
	}

	private fun registerCommands() {
		for ((command, function) in commands) {
			CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
				dispatcher.register(literal(command).executes(function))
			}
		}
	}
}
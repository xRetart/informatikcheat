package com.cheat

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.*
import net.minecraft.server.command.ServerCommandSource



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

	override fun onInitializeClient() {
		registerCommands()
	}

	private fun registerCommands() {
		for ((command, function) in commands) {
			CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
				dispatcher.register(literal(command).executes(function))
			}
		}
	}
}
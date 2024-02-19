package com.cheat

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.*
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text



object InformatikCheatClient : ClientModInitializer {
	private val commands: Map<String, (CommandContext<ServerCommandSource>) -> Int> = mapOf(
			"autosprint" to { context -> enabledCheats.toggle(CheatKind.AUTO_SPRINT, context) },
			"autoeat" to { context -> enabledCheats.toggle(CheatKind.AUTO_EAT, context) },
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
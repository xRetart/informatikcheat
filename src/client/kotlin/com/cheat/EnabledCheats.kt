package com.cheat

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object enabledCheats {
    private var table: MutableMap<CheatKind, Boolean> = mutableMapOf(
            CheatKind.AUTO_SPRINT to false,
            CheatKind.AUTO_EAT to false,
            CheatKind.AUTO_SOUP to false,
            CheatKind.AUTO_POTION to false,
            CheatKind.KILL_AURA to false,
            CheatKind.ANTI_AFK to false,
            CheatKind.XRAY to false,
            CheatKind.NO_CLIP to false,
    )
    fun toggle(cheat: CheatKind, context: CommandContext<ServerCommandSource>): Int {
        val status = table[cheat]!!
        val action = if (status) "disable" else "enable"
        table[cheat] = !status

        context.source.sendFeedback({ Text.literal("$action $cheat\n") }, false)
        return 1
    }
    fun list(context: CommandContext<ServerCommandSource>): Int {
        val tableString = StringBuilder("enabled cheats: ")
        for ((kind, status) in table) {
            tableString.append("\n$kind : $status")
        }
        context.source.sendFeedback({ Text.literal(tableString.toString() + '\n') }, false)
        return 1
    }
}
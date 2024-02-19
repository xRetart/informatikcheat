package com.cheat

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object enabledCheats {
    private var table: MutableMap<CheatKind, Boolean> = mutableMapOf(
            CheatKind.AUTO_SPRINT to false,
            CheatKind.AUTO_EAT to false,
    )
    fun toggle(cheat: CheatKind, context: CommandContext<ServerCommandSource>): Int {
        val status = table[cheat]!!
        val action = if (status) "enable" else "disable"
        table[cheat] = !(status)

        context.source.sendFeedback({ Text.literal("$action $cheat") }, false)
        return 1
    }
    fun list(context: CommandContext<ServerCommandSource>): Int {
        val tableString = StringBuilder("enabled cheats: ")
        for ((kind, status) in table) {
            tableString.append("\n$kind : $status")
        }
        context.source.sendFeedback({ Text.literal(tableString.toString()) }, false)
        return 1
    }
}
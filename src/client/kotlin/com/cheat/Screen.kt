package com.cheat

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text


@Environment(EnvType.CLIENT)
class TutorialScreen : Screen(Text.literal("My tutorial screen")) {
    var button1: ButtonWidget? = null

    override fun init() {
        button1 = ButtonWidget.builder(Text.literal("autosprint")) { button ->
            val before = features["autosprint"]!!.isEnabled
            features["autosprint"]!!.isEnabled = !before
        }
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button1")))
                .build()

        addDrawableChild(button1)
    }
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("You must see me"), width / 2, height / 2, 0xffffff)
    }
}
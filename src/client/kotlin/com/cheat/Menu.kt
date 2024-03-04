package com.cheat

import com.cheat.features.Feature
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text


@Environment(EnvType.CLIENT)
class MenuScreen : Screen(Text.literal("Cheat Menu")) {
    private var buttons: List<ButtonWidget>? = null

    override fun init() {
        buttons =
            features.toList().mapIndexed { index, (featureName, feature) -> createButton(featureName, feature, index) }
        buttons!!.forEach(::addDrawableChild)
    }

    private fun createButton(featureName: String, feature: Feature, position: Int): ButtonWidget {
        val x = if (position % 2 == 0) 20 else 240
        val y = 10 + position / 2 * 30

        return ButtonWidget.builder(Text.literal(featureName)) { feature.toggle() }
            .dimensions(x, y, 200, 20)
            .tooltip(Tooltip.of(Text.literal(feature.description)))
            .build()
    }
}
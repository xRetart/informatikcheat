package com.cheat

import com.cheat.features.Feature
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text


// Die Klasse definiert das Aussehen und Verhalten des Cheat Menus.
// Das Menu besteht aus einem zweispaltigen Gitter aus Knöpfen für alle registrierten Features.
@Environment(EnvType.CLIENT)
class MenuScreen : Screen(Text.literal("Cheat Menu")) {
    private var buttons: List<ButtonWidget>? = null  // Liste der Knopf-Widgets

    // erstellt ein neues Menu
    override fun init() {
        // erstellt eine neue Liste von Knöpfen, indem für jedes Feature `createButton` aufgerufen wird
        buttons =
            features.toList().mapIndexed { index, (featureName, feature) -> createButton(featureName, feature, index) }
        buttons!!.forEach(::addDrawableChild)
    }

    // erstellt ein neues Knopf-Widget anhand eines Features und der Position in dem Gitter
    private fun createButton(featureName: String, feature: Feature, position: Int): ButtonWidget {
        val x = if (position % 2 == 0) 20 else 240  // Jeder Knopf hat links und rechts von sich 20 pixel Abstand.
        val y = 10 + position / 2 * 30  // Reihen haben jeweils 10 pixel Abstand.

        return ButtonWidget.builder(Text.literal(featureName)) { feature.toggle() }  // Beim Klicken des Knopfes wird das Feature umgeschaltet mit `toggle()`.
            .dimensions(x, y, 200, 20)  // Der Knopf hat eine Breite von 200 Pixeln und eine Höhe von 20.
            .tooltip(Tooltip.of(Text.literal(feature.description)))  // Zeigt beim Schweben mit der Maus die Beschreibung des Features.
            .build()
    }
}
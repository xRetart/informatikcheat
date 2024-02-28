package com.cheat.features

interface Feature {
    var isEnabled: Boolean
    fun register()
}
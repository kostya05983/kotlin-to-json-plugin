package it.zoo.kotlin.to.json.idea.plugin.toolwindow

import javax.swing.JPanel

class KotlinToJsonToolWindow {
    private lateinit var mainToolWindow: JPanel

    fun getContent(): JPanel {
        return mainToolWindow
    }
}
package it.zoo.kotlin.to.json.idea.plugin.toolwindow

import javax.swing.JPanel
import javax.swing.JTextArea

class KotlinToJsonToolWindow {
    private var mainToolWindow: JPanel = JPanel()

    private lateinit var kotlinDataClassInput: JTextArea

    init {
//        val textArea = JBTextArea("FFFF")
//        mainToolWindow.add(textArea)
    }

    fun getContent(): JPanel {
        return mainToolWindow
    }
}
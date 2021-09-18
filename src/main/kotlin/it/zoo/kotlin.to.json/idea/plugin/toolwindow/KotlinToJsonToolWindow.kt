package it.zoo.kotlin.to.json.idea.plugin.toolwindow

import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField

class KotlinToJsonToolWindow {
    private lateinit var mainToolWindow: JPanel

    private lateinit var kotlinDataClassInput: JTextArea
    private lateinit var convertButton: JButton
    private lateinit var text: JTextField

    init {
//        val textArea = JBTextArea("FFFF")
//        mainToolWindow.add(textArea)
    }

    fun getContent(): JPanel {
        return mainToolWindow
    }
}
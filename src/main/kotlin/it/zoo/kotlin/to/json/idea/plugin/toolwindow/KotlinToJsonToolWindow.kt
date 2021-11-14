package it.zoo.kotlin.to.json.idea.plugin.toolwindow

import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField

class KotlinToJsonToolWindow {
    private lateinit var mainToolWindow: JPanel

    private lateinit var kotlinDataClassInput: JTextArea
    private lateinit var kotlinJsonOutput: JTextArea
    private lateinit var convertButton: JButton
    private lateinit var patternText: JTextField

    init {
        convertButton.addMouseListener(object: MouseListener {
            override fun mouseClicked(e: MouseEvent?) {
            }

            override fun mousePressed(e: MouseEvent?) {
            }

            override fun mouseReleased(e: MouseEvent?) {
            }

            override fun mouseEntered(e: MouseEvent?) {
            }

            override fun mouseExited(e: MouseEvent?) {
            }

        })
    }

    fun getContent(): JPanel {
        return mainToolWindow
    }
}
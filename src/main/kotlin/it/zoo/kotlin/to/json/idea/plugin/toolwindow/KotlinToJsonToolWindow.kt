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
                TODO("Not yet implemented")
            }

            override fun mousePressed(e: MouseEvent?) {

                TODO("Not yet implemented")
            }

            override fun mouseReleased(e: MouseEvent?) {
                TODO("Not yet implemented")
            }

            override fun mouseEntered(e: MouseEvent?) {
                TODO("Not yet implemented")
            }

            override fun mouseExited(e: MouseEvent?) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getContent(): JPanel {
        return mainToolWindow
    }
}
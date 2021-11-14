package it.zoo.kotlin.to.json.idea.plugin.toolwindow

import it.zoo.kotlin.to.json.idea.plugin.algo.KotlinOutputConverter
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

    private val outputConverter = KotlinOutputConverter()

    init {
        convertButton.addMouseListener(object: MouseListener {
            override fun mouseClicked(e: MouseEvent?) {
                val trimmedInput = kotlinDataClassInput.text.trim()
                val trimmedPattern = patternText.text.trim()
                val output = outputConverter.convert(trimmedInput, trimmedPattern)
                kotlinJsonOutput.text= output
            }

            override fun mousePressed(e: MouseEvent?) {
            }

            override fun mouseReleased(e: MouseEvent?) {
            }

            override fun mouseEntered(e: MouseEvent?) {
                if (kotlinDataClassInput.text == START_INPUT_TEXT) {
                    kotlinDataClassInput.text = ""
                }
            }

            override fun mouseExited(e: MouseEvent?) {
            }

        })
    }

    fun getContent(): JPanel {
        return mainToolWindow
    }

    private companion object {
        const val START_INPUT_TEXT = "Input your kotlin text here"
    }
}
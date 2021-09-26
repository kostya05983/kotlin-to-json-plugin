package it.zoo.kotlin.to.json.idea.plugin.algo

class JsonBuilder {
    private val sb = StringBuilder()
    private val buffer = StringBuilder()

    fun startObject() {
        buffer.append("{")
    }

    fun endObject() {
        buffer.append("}")
    }

    fun startName() {
        buffer.append("\"")
    }

    fun endName() {
        buffer.append("\"")
    }

    fun startString() {
        buffer.append("\"")
    }

    fun endString() {
        buffer.append("\"")
    }

    fun valueDelimiter() {
        buffer.append(",")
    }

    fun addChar(char: Char) {
        buffer.append(char)
    }

    fun delimiter() {
        buffer.append(":")
    }

    fun cleanBuffer() {
        buffer.clear()
    }

    fun flush() {
        sb.append(buffer.toString())
        buffer.clear()
    }

    override fun toString(): String {
        return sb.toString()
    }
}
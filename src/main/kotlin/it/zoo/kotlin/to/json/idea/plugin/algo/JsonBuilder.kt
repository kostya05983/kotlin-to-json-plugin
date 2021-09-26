package it.zoo.kotlin.to.json.idea.plugin.algo

class JsonBuilder {
    private val sb = StringBuilder()

    fun startObject() {
        sb.append("{")
    }

    fun endObject() {
        sb.append("}")
    }

    fun startName() {
        sb.append("\"")
    }

    fun endName() {
        sb.append("\"")
    }

    fun startString() {
        sb.append("\"")
    }

    fun endString() {
        sb.append("\"")
    }

    fun valueDelimiter() {
        sb.append(",")
    }

    fun addChar(char: Char) {
        sb.append(char)
    }

    fun delimiter() {
        sb.append(":")
    }

    override fun toString(): String {
        return sb.toString()
    }
}
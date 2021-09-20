package it.zoo.kotlin.to.json.idea.plugin.algo

class JsonBuilder {
    private val sb = StringBuilder()

    fun startObject() {
        sb.append("{")
    }

    fun closeObject() {
        sb.append("}")
    }

    fun nextName(name: String) {
        sb.append("\"$name\"")
    }

    fun delimiter() {
        sb.append(":")
    }
}
package it.zoo.kotlin.to.json.idea.plugin.algo

class JsonBuilder {
    private val sb = StringBuilder()

    fun startObject() {
        sb.append("{")
    }

    fun closeObject() {
        sb.append("}")
    }


}
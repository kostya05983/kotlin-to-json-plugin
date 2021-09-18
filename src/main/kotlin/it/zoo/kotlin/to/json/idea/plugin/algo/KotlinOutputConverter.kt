package it.zoo.kotlin.to.json.idea.plugin.algo

class KotlinOutputConverter {

    fun convert(input: String): String {
        var remainPart = input
        val jsonBuilder = StringBuilder()
        when {
            CLASS_REGEX.matches(remainPart) -> {
                val match = CLASS_REGEX.matchEntire(remainPart)!!
                val innerPartGroup = match.groups[0]
                val innerPartValue = innerPartGroup?.value!!
                jsonBuilder.append("{")
                remainPart = innerPartValue
            }
        }

        return ""
    }


    private companion object {
        val INT_REGEX = Regex("\\d*")
        val DOUBLE_REGEX = Regex("\\d*.\\d*")
        val CLASS_REGEX = Regex("\\w*\\((\\w*)\\)")
    }
}
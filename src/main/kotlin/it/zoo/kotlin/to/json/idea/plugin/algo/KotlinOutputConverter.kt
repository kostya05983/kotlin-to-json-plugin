package it.zoo.kotlin.to.json.idea.plugin.algo

import com.jetbrains.rd.util.CommonsLoggingLoggerFactory
import com.jetbrains.rd.util.info

class KotlinOutputConverter {
    private val logger = CommonsLoggingLoggerFactory.getLogger("plugin")

    /**
     * Test(a=ttt, a1=20, a2=30.0, t3=2021-09-19T09:54:38.162Z, test=Test2(i=2021-09-19))
     */
    fun convert(input: String): String {
        var remainPart = input

        if (CLASS_REGEX.matches(input).not()) {
            logger.info { "Input doesn't match format" }
            return "Input doesn't match format, please specify another input"
        }

        val matchResult = requireNotNull(CLASS_REGEX.matchEntire(input)) { "MatchResult must not be null" }
        val matchValue = requireNotNull(matchResult.groups[0]?.value) { "Match value must not be null" }

        val jsonBuilder = StringBuilder()
        jsonBuilder.append("{")

        for (char in matchValue) {
            if (char.isLetter()) {

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
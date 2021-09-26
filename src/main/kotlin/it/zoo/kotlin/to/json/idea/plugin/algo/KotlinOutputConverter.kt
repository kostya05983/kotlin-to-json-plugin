package it.zoo.kotlin.to.json.idea.plugin.algo

import com.jetbrains.rd.util.CommonsLoggingLoggerFactory
import com.jetbrains.rd.util.info

class KotlinOutputConverter {
    private val logger = CommonsLoggingLoggerFactory.getLogger("plugin")

    /**
     * Test(a=ttt, a1=20, a2=30.0, t3=2021-09-19T09:54:38.162Z, test=Test2(i=2021-09-19))
     */
    fun convert(input: String, pattern: String): String {
        if (CLASS_REGEX.matches(input).not()) {
            logger.info { "Input doesn't match format" }
            return "Input doesn't match format, please specify another input"
        }

        val matchResult = requireNotNull(CLASS_REGEX.matchEntire(input)) { "MatchResult must not be null" }
        val matchValue = requireNotNull(matchResult.groups[0]?.value) { "Match value must not be null" }

        val formattedValue = matchValue.substringAfter("(")

        val jsonBuilder = JsonBuilder()
        jsonBuilder.startObject()

        var currentState = START_READ_NAME
        for (char in formattedValue) {
            when {
                char.isLetter() -> {
                    when (currentState) {
                        START_READ_NAME -> {
                            jsonBuilder.startName()
                            jsonBuilder.addChar(char)
                            currentState = READ_NAME
                        }
                        READ_NAME -> {
                            jsonBuilder.addChar(char)
                        }
                        START_READ_VALUE -> {
                            jsonBuilder.startString()
                            jsonBuilder.addChar(char)
                            currentState = READ_STRING_VALUE
                        }
                        READ_STRING_VALUE -> {
                            jsonBuilder.addChar(char)
                        }
                        READ_INT_VALUE -> { // TODO for dates with T
                            jsonBuilder.addChar(char)
                        }
                    }
                }
                char.isDigit() -> {
                    when (currentState) {
                        READ_NAME -> {
                            jsonBuilder.addChar(char)
                        }
                        START_READ_VALUE -> {
                            jsonBuilder.addChar(char)
                            currentState = READ_INT_VALUE
                        }
                        READ_INT_VALUE -> {
                            jsonBuilder.addChar(char)
                        }
                    }
                }
                char == '.' -> {
                    jsonBuilder.addChar(char)
                }
                char == ' ' -> {
                }
                char == '=' -> {
                    jsonBuilder.endName()
                    jsonBuilder.delimiter()
                    jsonBuilder.flush()

                    currentState = START_READ_VALUE
                }
                char == ',' && jsonBuilder.isDate() -> {
                    jsonBuilder.dateFormatBuffer(pattern)
                    jsonBuilder.valueDelimiter()
                    jsonBuilder.flush()
                    currentState = START_READ_NAME
                }
                char == ',' -> {
                    when (currentState) {
                        READ_STRING_VALUE -> {
                            jsonBuilder.endString()
                            jsonBuilder.valueDelimiter()
                            jsonBuilder.flush()
                            currentState = START_READ_NAME
                        }
                        READ_INT_VALUE -> {
                            jsonBuilder.valueDelimiter()
                            jsonBuilder.flush()
                            currentState = START_READ_NAME
                        }
                    }
                }
                char == ')' && jsonBuilder.isDate() -> {
                    jsonBuilder.dateFormatBuffer(pattern)
                    jsonBuilder.endObject()
                    jsonBuilder.flush()
                }
                char == ')' -> {
                    jsonBuilder.endObject()
                    jsonBuilder.flush()
                }

                char == '(' -> {
                    jsonBuilder.cleanBuffer()
                    jsonBuilder.startObject()
                    currentState = START_READ_NAME
                }
                else -> {
                    jsonBuilder.addChar(char)
                }
            }
        }

        return jsonBuilder.toString()
    }


    private companion object {
        val CLASS_REGEX = Regex("\\w*\\([A-z=.0-9-, :)(]*\\)")
        private const val START_READ_NAME = 0
        private const val START_READ_VALUE = 2
        private const val READ_NAME = 1
        private const val READ_STRING_VALUE = 3
        private const val READ_INT_VALUE = 4
    }
}
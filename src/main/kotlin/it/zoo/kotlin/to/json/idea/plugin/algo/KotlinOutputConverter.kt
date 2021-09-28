package it.zoo.kotlin.to.json.idea.plugin.algo

import com.jetbrains.rd.util.CommonsLoggingLoggerFactory
import com.jetbrains.rd.util.info

class KotlinOutputConverter {
    private val logger = CommonsLoggingLoggerFactory.getLogger("plugin")

    fun convert(input: String, pattern: String): String {
        if (CLASS_REGEX.matches(input).not() && ARRAY_REGEX.matches(input).not()) {
            logger.info { "Input doesn't match format" }
            return "Input doesn't match format, please specify another input"
        }
        val matchValue = requireNotNull(getMatchResult(input)) { "Match value must not be null" }

        val preProcessed = when {
            CLASS_REGEX.matches(input) -> {
                matchValue
            }
            ARRAY_REGEX.matches(input) -> {
                preprocessArray(matchValue)
            }
            else -> matchValue
        }

        val jsonBuilder = JsonBuilder()
        var currentState = START_READ_NAME
        for (char in preProcessed) {
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
                char == '[' -> {
                    jsonBuilder.startArray()
                    jsonBuilder.flush()
                }
                char ==']' -> {
                    jsonBuilder.endArray()
                    jsonBuilder.flush()
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

    private fun preprocessArray(input: String): String {
        val sb = StringBuilder()
        var level = 0
        var ignore = false
        for(char in input) {
            when{
                char =='[' -> {
                    sb.append(char)
                    ignore = true
                }
                char == '(' -> {
                    level++
                    sb.append(char)
                    ignore = false
                }
                char == ')' -> {
                    level--
                    sb.append(char)
                    if (level ==0) {
                        ignore = true
                    }
                }
                char == ',' -> {
                    sb.append(char)
                }
                char == ']' -> {
                    sb.append(char)
                }
                else -> {
                    if (ignore.not()) {
                        sb.append(char)
                    }
                }
            }
        }
        return sb.toString()
    }

    private fun getMatchResult(input: String): String? {
        return when {
            ARRAY_REGEX.matches(input) -> {
                ARRAY_REGEX.matchEntire(input)?.groups?.get(0)?.value
            }
            CLASS_REGEX.matches(input) -> {
                CLASS_REGEX.matchEntire(input)?.groups?.get(0)?.value
            }
            else -> null
        }
    }


    private companion object {
        val ARRAY_REGEX = Regex("\\[\\w*\\([a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/? А-я]*\\)]")
        val CLASS_REGEX = Regex("\\w*\\([a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/? А-я]*\\)")
        private const val START_READ_NAME = 0
        private const val START_READ_VALUE = 2
        private const val READ_NAME = 1
        private const val READ_STRING_VALUE = 3
        private const val READ_INT_VALUE = 4
    }
}
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

        val preProcessed = applyRegexes(matchValue)

        val jsonBuilder = JsonBuilder()
        var currentState = START_READ_NAME
        var lastSymbol = '='
        for (char in preProcessed) {
            when {
                char.isLetter() || char.isDigit() -> {
                    when (currentState) {
                        START_READ_NAME -> {
                            jsonBuilder.addChar(char)
                            currentState = READ_NAME
                        }
                        READ_NAME -> {
                            jsonBuilder.addChar(char)
                        }
                        START_READ_VALUE -> {
                            jsonBuilder.addChar(char)
                            currentState = READ_VALUE
                        }
                        READ_VALUE -> {
                            jsonBuilder.addChar(char)
                        }
                    }
                }
                char == '[' -> {
                    jsonBuilder.startArray()
                    jsonBuilder.flush()
                }
                char ==']' -> {
                    if (jsonBuilder.isNumber().not() && jsonBuilder.isNull().not()) {
                        jsonBuilder.flushAsString()
                    } else {
                        jsonBuilder.flush()
                    }
                    jsonBuilder.endArray()

                    jsonBuilder.flush()
                }
                char == '.' -> {
                    jsonBuilder.addChar(char)
                }
                char == ' ' -> {
                }
                char == '=' -> {
                    jsonBuilder.flushAsString()
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
                        START_READ_VALUE, READ_NAME, READ_VALUE -> {
                            if(lastSymbol == '=') {
                                jsonBuilder.addNull()
                            }

                            if (jsonBuilder.isNumber().not() && jsonBuilder.isNull().not()) {
                                jsonBuilder.flushAsString()
                                jsonBuilder.valueDelimiter()
                                jsonBuilder.flush()
                            } else {
                                jsonBuilder.valueDelimiter()
                                jsonBuilder.flush()
                            }
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
                    when(currentState) {
                        START_READ_VALUE, READ_VALUE -> {
                            if (lastSymbol == '=') {
                                jsonBuilder.addNull()
                            }

                            if (jsonBuilder.isNumber().not() && jsonBuilder.isNull().not()) {
                                jsonBuilder.flushAsString()
                            } else {
                                jsonBuilder.flush()
                            }
                        }
                        READ_NAME -> {
                            throw RuntimeException("Wrong data format, value must be after json name")
                        }
                    }
                    jsonBuilder.endObject()
                    jsonBuilder.flush()
                }

                char == '(' -> {
                    jsonBuilder.cleanBuffer()
                    jsonBuilder.startObject()
                    jsonBuilder.flush()
                    currentState = START_READ_NAME
                }
                else -> {
                    jsonBuilder.addChar(char)
                }
            }
            lastSymbol = char
        }

        return jsonBuilder.toString()
    }

    private fun applyRegexes(input: String): String {
        return input.let { REPLACE_NULL_WITH_COMMA_REGEX.replace(it, "") }.let {
            REPLACE_NULL_REGEX.replace(it, "")
        }
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
        val ARRAY_REGEX = Regex("\\[\\w*[A-z0-9_, .a-zA-Z0-9!@#\$%^&*()+\\-=\\[\\]{};':\"\\\\|<>?А-я]*\\]")
        val CLASS_REGEX = Regex("\\w*\\([a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/? А-я]*\\)")

        val REPLACE_NULL_WITH_COMMA_REGEX = Regex(",\\s*\\w*=null")
        val REPLACE_NULL_REGEX = Regex("\\s*\\w*=null")

        private const val START_READ_NAME = 0
        private const val START_READ_VALUE = 2
        private const val READ_NAME = 1
        private const val READ_VALUE = 3
    }
}
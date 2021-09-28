package it.zoo.kotlin.to.json.idea.plugin.algo

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class JsonBuilder {
    private val sb = StringBuilder()
    private val buffer = StringBuilder()

    fun startObject() {
        buffer.append("{")
    }

    fun endObject() {
        buffer.append("}")
    }

    fun startArray() {
        buffer.append("[")
    }

    fun endArray() {
        buffer.append("]")
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

    fun dateFormatBuffer(format: String) {
        val formatter = DateTimeFormatter.ofPattern(format)
            .withLocale( Locale.UK )
            .withZone( ZoneId.systemDefault() );
        val formatted = when {
            LOCAL_DATE_REGEX.matches(buffer.toString()) -> {
                val parsed = LocalDate.parse(buffer.toString())
                formatter.format(parsed)
            }
            INSTANT_REGEX.matches(buffer.toString()) -> {
                val parsed = Instant.parse(buffer.toString())
                formatter.format(parsed)
            }
            LOCAL_DATE_TIME_REGEX.matches(buffer.toString()) -> {
                val parsed = LocalDateTime.parse(buffer.toString())
                formatter.format(parsed)
            }
            else -> {
                throw RuntimeException("Unsupported date")
            }
        }
        buffer.clear()
        buffer.append("\"$formatted\"")
        return
    }

    fun isDate(): Boolean {
        val localDateMatch = LOCAL_DATE_REGEX.containsMatchIn(buffer.toString())
        val localDateTimeMatch = LOCAL_DATE_TIME_REGEX.containsMatchIn(buffer.toString())
        val instantMatch = INSTANT_REGEX.containsMatchIn(buffer.toString())

        return localDateMatch.or(instantMatch).or(localDateTimeMatch)
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

    private companion object {
        val LOCAL_DATE_REGEX = Regex("\\d{4}-\\d{2}-\\d{2}")
        val LOCAL_DATE_TIME_REGEX = Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}")
        val INSTANT_REGEX = Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z")
    }
}
package it.zoo.kotlin.to.json.idea.plugin.algo

import org.junit.jupiter.api.Test

internal class KotlinOutputConverterTest {
    private val kotlinOutputConverter = KotlinOutputConverter()

    @Test
    fun `should success convert`() {
        val output = kotlinOutputConverter.convert("Test(a=ttt, a1=20, a2=30.0, t3=2021-09-19T09:54:38.162Z, test=Test2(i=2021-09-19))", "yyyy-MM-dd")
        println(output)
    }
}
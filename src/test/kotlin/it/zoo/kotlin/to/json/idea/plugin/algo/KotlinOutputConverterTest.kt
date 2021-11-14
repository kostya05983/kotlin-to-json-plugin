package it.zoo.kotlin.to.json.idea.plugin.algo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class KotlinOutputConverterTest {
    private val kotlinOutputConverter = KotlinOutputConverter()

    @Test
    fun `should success convert`() {
        val output = kotlinOutputConverter.convert("Test(a=ttt, a1=20, a2=30.0, t3=2021-09-19T09:54:38.162Z, test=Test2(i=2021-09-19))", "yyyy-MM-dd")
        assertEquals("{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-19\",\"test\":{\"i\":\"2021-09-19\"}}", output)
    }

    @Test
    fun `should success convert second example`() {
        val output = kotlinOutputConverter.convert("Test(a=ttt, a1=20, a2=30.0, t3=2021-09-26T15:27:25.770Z, test=Test2(i=2021-09-26), test2=Test3(test=2021-09-26T15:27:25.784))", "yyyy-MM-dd")
        assertEquals("{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-26\",\"test\":{\"i\":\"2021-09-26\"},\"test2\":{\"test\":\"2021-09-26\"}}", output)
    }

    @Test
    fun `should convert array to json`() {
        val output = kotlinOutputConverter.convert("[Test(a=ttt, a1=20, a2=30.0, t3=2021-09-28T14:28:19.037Z, test=[Test2(i=2021-09-28)], test2=Test3(test=2021-09-28T14:28:19.055)), Test(a=ttt, a1=20, a2=30.0, t3=2021-09-28T14:28:19.037Z, test=[Test2(i=2021-09-28)], test2=Test3(test=2021-09-28T14:28:19.055))]", "yyyy-MM-dd")
        assertEquals("[{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-28\",\"test\":[{\"i\":\"2021-09-28\"}],\"test2\":{\"test\":\"2021-09-28\"}},{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-28\",\"test\":[{\"i\":\"2021-09-28\"}],\"test2\":{\"test\":\"2021-09-28\"}}]", output)
    }

    @Test
    fun `should success convert class with uuid`() {
        val output = kotlinOutputConverter.convert("Test(a=6de25b31-afec-4672-b25f-015cc091591d)", "yyyy-MM-dd")
        assertEquals("{\"a\":\"6de25b31-afec-4672-b25f-015cc091591d\"}", output)
    }

    @Test
    fun `should convert enum`() {
        val output = kotlinOutputConverter.convert("Test(a=CODE_24354235)", "yyyy-MM-dd")
        assertEquals("{a=CODE_24354235}", output)
    }
}
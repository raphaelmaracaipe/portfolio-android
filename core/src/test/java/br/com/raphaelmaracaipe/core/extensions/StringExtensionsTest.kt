package br.com.raphaelmaracaipe.core.extensions

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `when you want transform json (string) to model`() {
        val model = TestModel("this is one text")
        val json = Gson().toJson(model)

        val modelText = json.fromJSON<TestModel>()
        Assert.assertEquals(model.text, modelText.text)
    }

    @Test
    fun `when you want transform json (string) to array of model`() {
        val model = arrayOf(TestModel("this is one text"))
        val json = Gson().toJson(model)

        val modelText = json.fromJSON<Array<TestModel>>()
        Assert.assertEquals(model[0].text, modelText[0].text)
    }

}

data class TestModel(
    val text: String
)
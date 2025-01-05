package br.com.raphaelmaracaipe.core.extensions

import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import org.junit.Assert
import org.junit.Test

class ArrayListExtensionsTest {

    @Test
    fun `when want copy data of arrayList to another arrayList of type different`() {
        val testDestination: ArrayList<ContactEntity> = arrayListOf()
        val testSource: ArrayList<ContactResponse> = arrayListOf(
            ContactResponse("t", "p", "c")
        )

        testDestination.copyOf(testSource)

        Assert.assertEquals(testSource.size, testDestination.size)
    }

}
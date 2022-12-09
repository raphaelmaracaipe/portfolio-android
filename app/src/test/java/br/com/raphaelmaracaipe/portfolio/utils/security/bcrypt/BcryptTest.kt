package br.com.raphaelmaracaipe.portfolio.utils.security.bcrypt

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BcryptTest {

    private val bcrypt: Bcrypt = BcryptImpl()

    @Test
    fun `when transform text in hash`() {
        val textPlain = "test"
        val hash = bcrypt.crypt(textPlain)
        Assert.assertTrue(bcrypt.check(textPlain, hash))
    }

}
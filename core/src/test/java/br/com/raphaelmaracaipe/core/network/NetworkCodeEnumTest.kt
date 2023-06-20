package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.network.NetworkCodeEnum.*
import org.junit.Assert
import org.junit.Test

class NetworkCodeEnumTest {

    @Test
    fun `check enum error of network` () {
        val checkEnum = NetworkCodeEnum.findEnumUsingCodeError(1000)
        Assert.assertEquals(ERROR_GENERAL, checkEnum)
    }

}
package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.sp.AuthSP
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {

    private lateinit var authSP: AuthSP
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authSP = mockk()
        authRepository = AuthRepositoryImpl(authSP)
    }

    @Test
    fun `when save phone in sp`() {
        every { authSP.setPhone(any()) } returns Unit
        every { authSP.getPhone() } returns "123456"

        authRepository.setPhone("123456")
        val result = authRepository.getPhone()
        assert(result == "123456")
    }

    @Test
    fun `when consult if phone is saved`() {
        every { authSP.getPhone() } returns "123456"

        assertTrue(authRepository.checkIfIsPhoneSaved())
    }

}
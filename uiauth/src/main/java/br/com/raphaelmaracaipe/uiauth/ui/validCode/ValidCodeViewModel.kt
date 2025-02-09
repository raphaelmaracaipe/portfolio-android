package br.com.raphaelmaracaipe.uiauth.ui.validCode

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.USER_SEND_CODE_INVALID
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.core.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidCodeViewModel @Inject constructor(
    private val context: Context,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _countDownTime: MutableLiveData<String> = MutableLiveData()
    val countDownTimer: LiveData<String> = _countDownTime

    private val _countDownPercentage: MutableLiveData<Int> = MutableLiveData()
    val countDownPercentage: LiveData<Int> = _countDownPercentage

    private val _code: MutableLiveData<String> = MutableLiveData()
    val code: LiveData<String> = _code

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _msgError: MutableLiveData<String> = MutableLiveData("")
    val msgError: LiveData<String> = _msgError

    private val _goToNext: MutableLiveData<Unit> = MutableLiveData()
    val goToNext: LiveData<Unit> = _goToNext

    fun sendToServer() = viewModelScope.launch {
        _showLoading.postValue(true)
        try {
            userRepository.validCode((code.value ?: ""))
            _goToNext.postValue(Unit)
        } catch (e: NetworkException) {
            val msg = when (e.translateCodeError()) {
                USER_SEND_CODE_INVALID -> R.string.response_error_code_invalid
                else -> R.string.response_error_code_general
            }
            _msgError.postValue(context.getString(msg))
        }
        _showLoading.postValue(false)
    }

    fun sendAgainToServer() = viewModelScope.launch {
        _showLoading.postValue(true)
        try {
            userRepository.sendCode(UserSendCodeRequest(authRepository.getPhone()))
            startCountDownTime()
        } catch (e: Exception) {
            _msgError.postValue(context.getString(R.string.code_send_again_invalid))
        }
        _showLoading.postValue(false)
    }

    fun startCountDownTime() {
        val timeTotal: Long = (1 * 60 * 1000)
        object : CountDownTimer(timeTotal, 1000) {

            override fun onTick(milliseconds: Long) {
                val rest = timeTotal - milliseconds
                val progress = ((rest.toDouble() / timeTotal.toDouble()) * 100).toInt()

                _countDownPercentage.postValue(progress)
                _countDownTime.postValue(convertMillisecondsToMinutesAndSeconds(milliseconds))
            }

            override fun onFinish() {
                _countDownPercentage.postValue(100)
            }

        }.start()
    }

    fun onTextChanged(text: CharSequence) {
        _msgError.postValue("")
        _code.postValue(text.toString())
    }

    private fun convertMillisecondsToMinutesAndSeconds(milliseconds: Long): String {
        val minutes = milliseconds / 60000
        val minutesInString = if (minutes >= 10) {
            minutes.toString()
        } else {
            "0$minutes"
        }

        val seconds = (milliseconds % 60000) / 1000
        val secondsInString = if (seconds >= 10) {
            seconds.toString()
        } else {
            "0$seconds"
        }

        return "$minutesInString:$secondsInString"
    }

}
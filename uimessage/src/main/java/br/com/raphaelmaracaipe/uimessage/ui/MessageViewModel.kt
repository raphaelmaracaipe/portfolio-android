package br.com.raphaelmaracaipe.uimessage.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.StatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val context: Context,
    private val statusRepository: StatusRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _doesConnected: MutableLiveData<Boolean> = MutableLiveData()
    val doesConnected: LiveData<Boolean> get() = _doesConnected

    private val _onIAmOnline: MutableLiveData<Unit> = MutableLiveData()
    val onIAmOnline: LiveData<Unit> = _onIAmOnline

    fun connect() = viewModelScope.launch {
        try {
            statusRepository.connect()
            _doesConnected.postValue(true)
        } catch (e: Exception) {
            e.printStackTrace()
            _doesConnected.postValue(false)
        }
    }

    fun consultStatus(phone: String) = viewModelScope.launch {
        try {
            statusRepository.checkStatus(phone)
            statusRepository.onIsHeOnline(phone) {
                _onIAmOnline.postValue(Unit)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun iAmOnline() = viewModelScope.launch {
        // TODO: APENAS PARA TESTE
        statusRepository.onIAmOnline("5561991939006")
//        statusRepository.onIAmOnline(authRepository.getPhone())
    }

}
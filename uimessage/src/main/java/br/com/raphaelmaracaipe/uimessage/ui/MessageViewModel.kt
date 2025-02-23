package br.com.raphaelmaracaipe.uimessage.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.StatusRepository
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.uimessage.flow.StatusNotificationFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val context: Context,
    private val statusRepository: StatusRepository,
    private val authRepository: AuthRepository,
    private val contactRepository: ContactRepository
) : ViewModel() {

    private val _doesConnected: MutableLiveData<Boolean> = MutableLiveData()
    val doesConnected: LiveData<Boolean> get() = _doesConnected

    private val _onIAmOnline: MutableLiveData<Unit> = MutableLiveData()
    val onIAmOnline: LiveData<Unit> = _onIAmOnline

    private val _onBack: MutableLiveData<Unit> = MutableLiveData()
    val onBack: LiveData<Unit> = _onBack

    private val _contact: MutableLiveData<ContactEntity> = MutableLiveData()
    val contact: LiveData<ContactEntity> = _contact

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
            with(statusRepository) {
                checkStatus(phone)
                onIsHeOnline(phone) {
                    CoroutineScope(Dispatchers.IO).launch {
                        contactRepository.lastSeen(phone)
                    }
                    _onIAmOnline.postValue(Unit)

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun iAmOnline() = viewModelScope.launch {
        statusRepository.onIAmOnline(authRepository.getPhone())
    }

    fun returnConsultFlow(phone: String) {
        CoroutineScope(Dispatchers.Main).launch {
            StatusNotificationFlow.statusFlow.collect {
                consultStatus(phone)
            }
        }
    }

    fun onClickBack() {
        _onBack.postValue(Unit)
    }

    fun consultDataAboutContact(contactPhone: String) = viewModelScope.launch {
        _contact.postValue(contactRepository.getContact(contactPhone))
    }

}
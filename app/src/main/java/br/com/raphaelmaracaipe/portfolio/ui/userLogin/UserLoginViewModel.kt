package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserLoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun registerLoginInDatabase(user: UserEntity) {
        viewModelScope.launch {
            try {
                userRepository.saveInDataBase(user)
                _success.postValue(true)
            } catch (e: Exception) {
                _success.postValue(false)
            }
        }
    }

}
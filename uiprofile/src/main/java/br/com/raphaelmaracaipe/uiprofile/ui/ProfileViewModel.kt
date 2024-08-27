package br.com.raphaelmaracaipe.uiprofile.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.response.ProfileGetResponse
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.uiprofile.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {

    private val profile = ProfileRequest()

    private val _msgError = MutableLiveData<String>()
    val msgError: LiveData<String> = _msgError

    private val _imagePreview = MutableLiveData<Bitmap?>()
    val imagePreview: LiveData<Bitmap?> = _imagePreview

    private val _isShowLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _profileSaved = MutableLiveData<Unit>()
    val profileSaved: LiveData<Unit> = _profileSaved

    private val _profileSavedServer = MutableLiveData<ProfileGetResponse>()
    val profileSavedServer: LiveData<ProfileGetResponse> = _profileSavedServer

    fun onTextChange(text: CharSequence) {
        profile.name = text.toString()
    }

    fun addImage(bitmap: Bitmap) {
        _imagePreview.postValue(bitmap)
    }

    fun markWhichProfileSaved(imageProfile: String) {
        profile.photo = imageProfile
        userRepository.markWhichProfileSaved()
    }

    fun cleanImageSelectedToPreview() {
        profile.photo = ""
        _imagePreview.postValue(null)
    }

    fun sendProfileToServer() = viewModelScope.launch {
        _isShowLoading.postValue(true)
        try {
            userRepository.profile(profile)
            _profileSaved.postValue(Unit)
        } catch (e: NetworkException) {
            _msgError.postValue(context.getString(R.string.error_to_send_profile))
        }
        _isShowLoading.postValue(false)
    }

    fun ifExistData(): Boolean = profile.name.isNotEmpty() || profile.photo.isNotEmpty()

    fun getProfileSavedInServer() = viewModelScope.launch {
        _isShowLoading.postValue(true)
        try {
            val profileOfServer = userRepository.getProfileSavedInServer()
            _profileSavedServer.postValue(profileOfServer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _isShowLoading.postValue(false)
    }

}
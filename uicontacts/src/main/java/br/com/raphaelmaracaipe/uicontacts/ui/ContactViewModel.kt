package br.com.raphaelmaracaipe.uicontacts.ui

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds
import android.provider.ContactsContract.Contacts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val context: Context,
    private val contactRepository: ContactRepository,
) : ViewModel() {

    private val _contactsOfSearch: MutableLiveData<ArrayList<ContactEntity>> = MutableLiveData()
    val contactsOfSearch: LiveData<ArrayList<ContactEntity>> = _contactsOfSearch

    private val _contacts: MutableLiveData<ArrayList<ContactEntity>> = MutableLiveData()
    val contacts: LiveData<ArrayList<ContactEntity>> = _contacts

    fun sendContactsToConsult() = viewModelScope.launch {
        try {
            contactRepository.consult(getContacts())
        } catch (e: NetworkException) {
            e.printStackTrace()
        }
    }

    fun contactSaved(itemPerPage: Int, pageCurrent: Int) = viewModelScope.launch {
        try {
            _contacts.postValue(
                contactRepository.contactSaved(itemPerPage, pageCurrent)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            _contacts.postValue(arrayListOf())
        }
    }

    fun searchItem(text: String, itemPerPage: Int, pageCurrent: Int) = viewModelScope.launch {
        try {
            _contactsOfSearch.postValue(contactRepository.searchItem(text, itemPerPage, pageCurrent))
        } catch (e: Exception) {
            e.printStackTrace()
            _contactsOfSearch.postValue(arrayListOf())
        }
    }

    @SuppressLint("Range")
    private fun getContacts(): ArrayList<String> {
        val contactList = arrayListOf<String>()
        context.contentResolver?.let { contentResolver ->
            val cursor = contentResolver.query(
                Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val hasPhoneNumber = it.getInt(it.getColumnIndex(Contacts.HAS_PHONE_NUMBER)) > 0
                    if (hasPhoneNumber) {
                        val id = it.getString(it.getColumnIndex(Contacts._ID))

                        val phoneCursor = contentResolver.query(
                            CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )

                        phoneCursor?.use { pc ->
                            while (pc.moveToNext()) {
                                val phoneNumber = pc.getString(
                                    pc.getColumnIndex(
                                        CommonDataKinds.Phone.NUMBER
                                    )
                                )

                                phoneNumber?.let { phone ->
                                    val phoneCleaned = phone.replace("[^\\d]".toRegex(), "")
                                    contactList.add(phoneCleaned)
                                }
                            }
                        }
                    }
                }
            }
        }

        return contactList
    }

}
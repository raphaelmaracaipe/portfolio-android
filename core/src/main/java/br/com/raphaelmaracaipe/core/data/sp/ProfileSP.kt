package br.com.raphaelmaracaipe.core.data.sp

interface ProfileSP {
    fun markWithExistProfile()

    fun isExistProfileSaved(): Boolean
}
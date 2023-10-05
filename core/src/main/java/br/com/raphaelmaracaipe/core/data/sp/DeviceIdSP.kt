package br.com.raphaelmaracaipe.core.data.sp

interface DeviceIdSP {
    fun save(deviceId: String)
    fun get(): String
}
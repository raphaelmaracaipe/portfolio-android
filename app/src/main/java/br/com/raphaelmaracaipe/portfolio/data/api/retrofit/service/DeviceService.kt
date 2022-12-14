package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service

import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestDeviceInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DeviceService {

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}"
    )
    @POST("/api/v1/device/")
    suspend fun sendInformationAboutDevice(@Body request: RequestDeviceInfo): Response<Void>

}
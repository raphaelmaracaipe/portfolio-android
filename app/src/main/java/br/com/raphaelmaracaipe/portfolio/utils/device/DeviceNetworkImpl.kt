package br.com.raphaelmaracaipe.portfolio.utils.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class DeviceNetworkImpl @Inject constructor(
    private val context: Context
): DeviceNetwork {

    override fun isNetworkConnected(): Boolean {
        try {
            val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val nc = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

                return when {
                    nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}
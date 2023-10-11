package br.com.raphaelmaracaipe.core.externals

object SpKeysExternal {

    external fun getTokenKey(): String?

    external fun getTokenEditAccessAndRefreshKey(): String?

    external fun getDeviceKey(): String?

    external fun getDeviceEditKey(): String?

}
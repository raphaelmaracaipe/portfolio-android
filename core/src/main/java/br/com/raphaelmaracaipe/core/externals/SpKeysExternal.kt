package br.com.raphaelmaracaipe.core.externals

object SpKeysExternal {

    external fun getTokenKey(): String?

    external fun getTokenEditAccessAndRefreshKey(): String?

    external fun getDeviceKey(): String?

    external fun getDeviceEditKey(): String?

    external fun getKeySp(): String?

    external fun getKeySpEdit(): String?

    external fun getProfileSpKey(): String?

    external fun getProfileEditSpKey(): String?

}
package com.branhamplayer.android.services.firebase

import com.branhamplayer.android.BuildConfig
import com.branhamplayer.android.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class RemoteConfigService {

    companion object {

        private val firebase: FirebaseRemoteConfig by lazy {
            FirebaseRemoteConfig.getInstance()
        }

        fun initializeInstance() {
            val settings = FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build()

            firebase.setConfigSettings(settings)
            firebase.setDefaults(R.xml.remote_config_defaults)

            firebase.fetch(RemoteConfigConstants.Defaults.cacheExpiration)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            firebase.activateFetched()
                        }
                    }
        }

        val instance: FirebaseRemoteConfig
            get() = firebase
    }
}
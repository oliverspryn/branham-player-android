package com.branhamplayer.android.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.branhamplayer.android.middleware.PreflightChecklistMiddleware
import com.branhamplayer.android.reducers.PreflightChecklistReducer
import com.branhamplayer.android.ui.PreflightChecklistFragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides

@Module
class PreflightChecklistModule(private val context: Context) {

    @Provides
    fun provideAlertDialogBuilder() = AlertDialog.Builder(context)

    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    @Provides
    fun providePreflightChecklistFragment() = PreflightChecklistFragment()

    @Provides
    fun providePreflightChecklistMiddleware(firebaseRemoteConfig: FirebaseRemoteConfig) =
        PreflightChecklistMiddleware(firebaseRemoteConfig)

    @Provides
    fun providePreflightChecklistReducer() = PreflightChecklistReducer()
}
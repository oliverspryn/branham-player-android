package com.branhamplayer.android.dagger

import com.branhamplayer.android.middleware.StartupMiddleware
import com.branhamplayer.android.reducers.StartupReducer
import com.branhamplayer.android.ui.StartupActivity
import dagger.Component

@Component(modules = [AuthenticationModule::class, PreflightChecklistModule::class, RoutingModule::class, StartupModule::class])
interface StartupComponent {
    fun inject(activity: StartupActivity)
    fun inject(rootMiddleware: StartupMiddleware)
    fun inject(rootReducer: StartupReducer)
}
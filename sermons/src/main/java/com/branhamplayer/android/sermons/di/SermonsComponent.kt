package com.branhamplayer.android.sermons.di

import com.branhamplayer.android.di.AuthenticationModule
import com.branhamplayer.android.sermons.middleware.SermonsMiddleware
import com.branhamplayer.android.sermons.reducers.SermonsReducer
import com.branhamplayer.android.sermons.ui.SermonListFragment
import com.branhamplayer.android.sermons.ui.SermonsActivity
import dagger.Component

@Component(modules = [AuthenticationModule::class, DataModule::class, DrawerModule::class, ProfileModule::class, RxJavaModule::class, SermonsModule::class])
interface SermonsComponent {
    fun inject(activity: SermonsActivity)
    fun inject(fragment: SermonListFragment)
    fun inject(rootMiddleware: SermonsMiddleware)
    fun inject(rootReducer: SermonsReducer)
}

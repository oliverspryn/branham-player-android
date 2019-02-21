package com.branhamplayer.android.di

import com.branhamplayer.android.base.di.ActivityScope
import com.branhamplayer.android.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [StartupModule::class])
interface StartupComponent {
    fun inject(activity: MainActivity)
}

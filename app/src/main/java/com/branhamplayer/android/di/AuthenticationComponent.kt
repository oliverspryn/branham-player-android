package com.branhamplayer.android.di

import com.branhamplayer.android.base.di.FragmentScope
import com.branhamplayer.android.base.di.MiddlewareScope
import com.branhamplayer.android.base.di.ReducerScope
import com.branhamplayer.android.middleware.AuthenticationMiddleware
import com.branhamplayer.android.reducers.AuthenticationReducer
import com.branhamplayer.android.ui.AuthenticationFragment
import dagger.Component

@FragmentScope
@MiddlewareScope
@ReducerScope
@Component(modules = [AuthenticationModule::class])
interface AuthenticationComponent {
    fun inject(fragment: AuthenticationFragment)
    fun inject(middleware: AuthenticationMiddleware)
    fun inject(reducer: AuthenticationReducer)
}
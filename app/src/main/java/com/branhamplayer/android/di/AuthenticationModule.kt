package com.branhamplayer.android.di

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.provider.CustomTabsOptions
import com.auth0.android.provider.WebAuthProvider
import com.branhamplayer.android.BuildConfig
import com.branhamplayer.android.reducers.AuthenticationReducer
import com.branhamplayer.android.utils.auth0.ProfileManager
import dagger.Module
import dagger.Provides

@Module
class AuthenticationModule {

    @Provides
    fun provideAuth0() = Auth0(BuildConfig.AUTH0_CLIENT_ID, BuildConfig.AUTH0_DOMAIN)

    @Provides
    fun provideAuth0Service(authClient: AuthenticationAPIClient, credentialsManager: CredentialsManager) =
        ProfileManager(authClient, credentialsManager)

    @Provides
    fun provideAuthenticationAPIClient(auth0: Auth0) = AuthenticationAPIClient(auth0)

    @Provides
    fun provideAuthenticationReducer(userCredentials: CredentialsManager) =
        AuthenticationReducer(userCredentials)

    @Provides
    fun provideCredentialsManager(authenticationAPIClient: AuthenticationAPIClient, context: Context) =
        CredentialsManager(authenticationAPIClient, SharedPreferencesStorage(context))

    @Provides
    fun provideCustomTabsOptionsBuilder(): CustomTabsOptions.Builder = CustomTabsOptions.newBuilder()

    @Provides
    fun provideWebAuthProviderBuilder(auth0: Auth0): WebAuthProvider.Builder = WebAuthProvider.init(auth0)
}

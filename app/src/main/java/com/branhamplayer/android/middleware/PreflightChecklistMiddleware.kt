package com.branhamplayer.android.middleware

import com.branhamplayer.android.BuildConfig
import com.branhamplayer.android.StartupConstants
import com.branhamplayer.android.actions.PreflightChecklistAction
import com.branhamplayer.android.actions.RoutingAction
import com.branhamplayer.android.base.redux.TypedMiddleware
import com.branhamplayer.android.services.Semver
import com.branhamplayer.android.states.StartupState
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.rekotlin.DispatchFunction
import javax.inject.Inject

class PreflightChecklistMiddleware @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : TypedMiddleware<PreflightChecklistAction, StartupState> {

    override fun invoke(dispatch: DispatchFunction, action: PreflightChecklistAction, oldState: StartupState?) {
        when (action) {
            is PreflightChecklistAction.CheckMessageAction -> checkMessage(dispatch)
            is PreflightChecklistAction.CheckMinimumVersionAction -> checkMinimumVersion(dispatch)
            is PreflightChecklistAction.CheckPlatformStatusAction -> checkPlatformStatus(dispatch)
        }
    }

    private fun checkMessage(dispatch: DispatchFunction) {
        val message = firebaseRemoteConfig.getString(StartupConstants.PreflightChecklist.message)

        if (message.isNullOrBlank()) {
            dispatch(RoutingAction.NavigateToAuthenticationAction)
        } else {
            dispatch(PreflightChecklistAction.NotifyWithMessageAction(message))
        }
    }

    private fun checkMinimumVersion(dispatch: DispatchFunction) {
        val appVersion = Semver(BuildConfig.VERSION_NAME)
        val minimumVersion = Semver(firebaseRemoteConfig.getString(StartupConstants.PreflightChecklist.minimumVersion))

        if (appVersion >= minimumVersion) {
            dispatch(PreflightChecklistAction.CheckMessageAction)
        } else {
            dispatch(PreflightChecklistAction.StopAppWithMinimumVersionFailureAction)
        }
    }

    private fun checkPlatformStatus(dispatch: DispatchFunction) =
        if (firebaseRemoteConfig.getBoolean(StartupConstants.PreflightChecklist.platformStatus)) {
            dispatch(PreflightChecklistAction.CheckMinimumVersionAction)
        } else {
            val message = firebaseRemoteConfig.getString(StartupConstants.PreflightChecklist.message)
            dispatch(PreflightChecklistAction.StopAppWithPlatformDownAction(message))
        }
}
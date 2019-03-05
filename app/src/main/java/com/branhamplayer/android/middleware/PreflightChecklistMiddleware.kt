package com.branhamplayer.android.middleware

import android.content.Context
import com.branhamplayer.android.BuildConfig
import com.branhamplayer.android.R
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
    private val context: Context,
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
            dispatch(PreflightChecklistAction.CheckMinimumVersionAction)
        } else {
            dispatch(PreflightChecklistAction.NotifyWithMessageAction(message))
        }
    }

    private fun checkMinimumVersion(dispatch: DispatchFunction) {
        val appVersion = Semver(BuildConfig.VERSION_NAME)
        val minimumVersion = Semver(firebaseRemoteConfig.getString(StartupConstants.PreflightChecklist.minimumVersion))

        if (appVersion >= minimumVersion) {
            dispatch(RoutingAction.NavigateToAuthenticationAction)
        } else {
            dispatch(PreflightChecklistAction.StopAppWithMinimumVersionFailureAction(context.getString(R.string.preflight_checklist_required_update)))
        }
    }

    private fun checkPlatformStatus(dispatch: DispatchFunction) =
        if (firebaseRemoteConfig.getBoolean(StartupConstants.PreflightChecklist.platformStatus)) {
            dispatch(PreflightChecklistAction.CheckMessageAction)
        } else {
            val message = firebaseRemoteConfig.getString(StartupConstants.PreflightChecklist.message)

            if (message.isNullOrBlank()) {
                dispatch(PreflightChecklistAction.StopAppWithPlatformDownAction(context.getString(R.string.preflight_checklist_platform_down)))
            } else {
                dispatch(PreflightChecklistAction.StopAppWithPlatformDownAction(message))
            }
        }
}

package tech.oliver.branhamplayer.android.sermons.middleware

import android.content.Context
import io.reactivex.Scheduler
import org.koin.standalone.StandAloneContext
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import tech.oliver.branhamplayer.android.sermons.actions.DrawerAction
import tech.oliver.branhamplayer.android.sermons.actions.ProfileAction
import tech.oliver.branhamplayer.android.sermons.shared.SermonsModuleConstants
import tech.oliver.branhamplayer.android.sermons.states.SermonsState
import tech.oliver.branhamplayer.android.services.auth0.Auth0Service

class ProfileMiddleware {
    companion object {

        fun process(): Middleware<SermonsState> = { dispatcher, getState ->
            { next ->
                { action ->
                    when (action) {
                        is ProfileAction.GetUserProfileAction -> getUserProfile(action.context, dispatcher, getState())
                    }

                    next(action)
                }
            }
        }

        private fun getUserProfile(context: Context, dispatch: DispatchFunction, state: SermonsState?) {

            if (state?.profile != null) {
                dispatch(DrawerAction.PopulateDrawerWithProfileAction(state.profile.name, state.profile.email))
                return
            }

            val auth0: Auth0Service = StandAloneContext.getKoin().koinContext.get()
            val bg: Scheduler = StandAloneContext.getKoin().koinContext.get(SermonsModuleConstants.BG_THREAD)
            val ui: Scheduler = StandAloneContext.getKoin().koinContext.get(SermonsModuleConstants.UI_THREAD)

            // TODO: Use a disposable
            auth0.getUserProfileInformation(context)
                    .subscribeOn(bg)
                    .observeOn(ui)
                    .subscribe({ profile ->
                        dispatch(DrawerAction.PopulateDrawerWithProfileAction(profile.name, profile.email))
                        dispatch(ProfileAction.SaveUserProfileAction(profile))
                    }, {
                        // TODO: Do nothing?
                    })
        }
    }
}
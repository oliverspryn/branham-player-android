package com.branhamplayer.android.sermons.actions

import com.branhamplayer.android.base.redux.BaseAction

sealed class SermonListAction : BaseAction {
    object FetchListAction : SermonListAction()

    object CheckFileReadPermissionAction : SermonListAction()
    object GetFileReadPermissionAction : SermonListAction()
    object ShowPermissionDeniedErrorAction : SermonListAction()
    object ShowPermissionNotYetRequestedAction : SermonListAction()
}

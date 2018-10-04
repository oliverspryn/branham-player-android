package tech.oliver.branhamplayer.android.sermons.models

import android.support.v4.media.session.PlaybackStateCompat
import tech.oliver.branhamplayer.android.models.Model

data class PlayerUpdateModel(
        val state: PlaybackStateCompat,
        val oldSermon: SermonState,
        val newSermon: SermonState
) : Model {

    data class SermonState(
        val id: String?,
        val duration: Int
    ) : Model
}
package tech.oliver.branhamplayer.android.sermons.repositories

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.oliver.branhamplayer.android.base.Repository
import tech.oliver.branhamplayer.android.sermons.services.logging.Loggly
import tech.oliver.branhamplayer.android.sermons.services.logging.LogglyConstants.Tags.REPOSITORY
import java.io.File

class SermonsRepository : Repository {

    private val sermons: MutableLiveData<List<File>> = MutableLiveData()

    fun getSermons(): LiveData<List<File>> {
        val sermonsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath
        val theTableDirectory = File(sermonsDirectory, "VGR")
        val sermonList = theTableDirectory.listFiles()

        Loggly.d(REPOSITORY,"Determined sermon directory is: ${theTableDirectory.absolutePath}")

        if (sermonList == null || sermonList.isEmpty()) {
            Loggly.i(REPOSITORY,"There are no available sermons")

            sermons.value = emptyList()
            return sermons
        }

        Loggly.d(REPOSITORY,"Found ${sermonList.size} sermons")

        sermons.value = sermonList.toList()
        return sermons
    }
}

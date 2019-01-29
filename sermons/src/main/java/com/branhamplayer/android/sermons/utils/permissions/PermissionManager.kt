package com.branhamplayer.android.sermons.utils.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.branhamplayer.android.services.logging.Loggly
import com.branhamplayer.android.services.logging.LogglyConstants.Tags.PERMISSIONS
import io.reactivex.Single

class PermissionManager(private val activity: Activity?) {

    fun getSinglePermission(permission: String): Single<Boolean> = Single.create { subscriber ->

        if (activity == null) {
            val message = "Cannot request permission '$permission', activity is null"

            Loggly.e(PERMISSIONS, message)
            subscriber.onError(ActivityNullException(message))
            return@create
        }

        if (ContextCompat.checkSelfPermission(activity.applicationContext, PermissionConstants.fileRead) == PackageManager.PERMISSION_GRANTED) {
            Loggly.i(PERMISSIONS, "Permission '$permission' previously granted")
            subscriber.onSuccess(true)
            return@create
        }

        subscriber.onSuccess(true) // TODO, re-evaluate once the build issue is fixed

//        Dexter.withActivity(activity)
//                .withPermission(permission)
//                .withListener(object : PermissionListener {
//
//                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
//                        Loggly.w(PERMISSIONS, "Permission '$permission' denied")
//                        subscriber.onSuccess(false)
//                    }
//
//                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//                        Loggly.i(PERMISSIONS, "Permission '$permission' granted")
//                        subscriber.onSuccess(true)
//                    }
//
//                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
//                        Loggly.i(PERMISSIONS, "Permission rationale triggered for '$permission'")
//                        token?.continuePermissionRequest()
//                    }
//                })
//                .check()
    }
}
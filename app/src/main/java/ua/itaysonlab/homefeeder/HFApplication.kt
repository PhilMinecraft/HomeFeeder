package ua.itaysonlab.homefeeder

import android.app.Application
import android.app.Notification
import ua.itaysonlab.homefeeder.utils.Logger
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import ua.itaysonlab.homefeeder.overlay.Overlay
import ua.itaysonlab.homefeeder.utils.UIBridge

class HFApplication: Application() {
    companion object {
        lateinit var instance: HFApplication
        val bridge = UIBridge()

        fun getAppNameByPkg(pkg: String): CharSequence {
            val ai = try {
                instance.packageManager.getApplicationInfo(pkg, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
            return if (ai != null) instance.packageManager.getApplicationLabel(ai) else "Unknown"
        }

        fun getSmallIcon(notification: Notification, pkg: String): Drawable {
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    notification.smallIcon.loadDrawable(instance)
                } else {
                    instance.packageManager.getResourcesForApplication(pkg).getDrawable(notification.icon, null)
                }
            } catch (e: Exception) {
                instance.packageManager.getApplicationIcon(pkg)
            }

        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.log("Application", "Starting HomeFeeder ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})...")
        instance = this
    }
}
package neomu.kotlin.common.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class FirebaseUtil {

    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    fun getFirebaseUserId() : String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun setFirebaseRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSetting = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSetting)
/*        mFirebaseRemoteConfig
                .fetch(0)
                .addOnCompleteListener(this, OnCompleteListener<Void?> { task ->
                    if (task.isSuccessful) {
                        mFirebaseRemoteConfig.activateFetched()
                    }
                    showDialogMessage()
                })*/
    }

    fun showDialogMessage(activity: Activity) {

        val splashBg = mFirebaseRemoteConfig.getString("Splash_background")
        val splashCaps = mFirebaseRemoteConfig.getBoolean("Splash_message_caps")
        val splashMessage = mFirebaseRemoteConfig.getString("Splash_message")

        if (splashCaps) {
//            val builder = AlertDialog.Builder(this)
//            builder.setMessage(Splash_message).setPositiveButton(getResources().getString("text_splash_btn_text".toInt()), DialogInterface.OnClickListener { dialog, which -> })
//            builder.create().show()
        } else { //caps가 false일경우 액티비티 이동
//            val intent = Intent(activity@ Spl/ashActivity, LoginActivity::class.java)
//            startActivity<MainActivity>{}
//            finish()
        }
    }
}
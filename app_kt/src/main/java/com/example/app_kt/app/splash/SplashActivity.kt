package neomu.kotlin.app.splash

import android.os.Bundle
import com.neomu.neomu.R
import com.neomu.neomu.common.activity.FirebaseUtil.FirebaseUtil

class SplashActivity : BaseActivity() {

    private lateinit var mFirebaseUtil: FirebaseUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }
}
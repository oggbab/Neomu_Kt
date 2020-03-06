package neomu.kotlin.app.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.app_kt.R
import com.example.app_kt.app.main.activity.AuthActivity
import neomu.kotlin.common.activity.BaseActivity
import neomu.kotlin.common.util.ValidationUtil
import neomu.kotlin.common.util.ValidationUtil.Companion.isSuccessSignInFirebaseAuth
import neomu.kotlin.common.util.ValidationUtilListener
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class LoginActivity: BaseActivity(), View.OnClickListener {

    lateinit var forInvalidChecklist : MutableList<String>
    lateinit var accountTextMap : MutableMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.login_btn -> {
                ValidationUtil.isValidate(forInvalidChecklist, object : ValidationUtilListener {
                    override fun onInvalid() {

                    }

                    override fun onSuccess() {
                        signInFirebaseAuth()
                    }

                })
            }
            R.id.main_linkText -> {
                startActivity(intentFor<AuthActivity>("logTest" to "onNewIntent() 실행")
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
        }
    }

    private fun signInFirebaseAuth() {
        isSuccessSignInFirebaseAuth(this, getUserIdForLogin(), getUserPwForLogin())
    }

    private fun getAccountForLogin() : MutableMap<String, String> {
        accountTextMap = mutableMapOf("userId" to getUserIdForLogin(), "userPw" to getUserPwForLogin())
        return accountTextMap
    }

    private fun getUserIdForLogin() : String {
        val name = findViewById<EditText>(R.id.login_id)?.text.toString()
        return if (name.isNullOrEmpty()) "" else name
    }

    private fun getUserPwForLogin() : String {
        val password = findViewById<EditText>(R.id.login_pw)?.text.toString()
        return if(password.isNullOrEmpty()) "" else password
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toast(intent?.getStringExtra("logTest").toString())
    }
}
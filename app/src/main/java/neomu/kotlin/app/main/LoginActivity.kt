package neomu.kotlin.app.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.neomu.neomu.R
import com.neomu.neomu.app.main.AuthActivity
import com.neomu.neomu.common.activity.BaseActivity
import neomu.kotlin.common.util.CommonUtil
import neomu.kotlin.common.util.ValidationUtil
import neomu.kotlin.common.util.ValidationUtilListener
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class LoginActivity: BaseActivity(), View.OnClickListener {

    lateinit var forInvalidChecklist : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.login_btn -> {
                getListForValidate()
                ValidationUtil.isValidate(forInvalidChecklist, object : ValidationUtilListener {
                    override fun onInvalid() {

                    }

                    override fun onSuccess() {

                    }

                })
            }
            R.id.main_linkText -> {
                startActivity(intentFor<AuthActivity>("logTest" to "onNewIntent() 실행")
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
        }
    }

    private fun getListForValidate() {
        val viewList = listOf<Int>(R.id.login_id, R.id.login_pw, R.id.main_join_email, R.id.main_join_nickName)
        for (i in viewList) {
            var resultText = viewList.get(i)?.toString()
            forInvalidChecklist.add(i, resultText)
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toast(intent?.getStringExtra("logTest").toString())
    }

}
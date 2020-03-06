package neomu.kotlin.app.main

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.app_kt.R
import com.example.app_kt.app.main.model.UserInfoModel
import com.example.app_kt.common.constanse.CommonConstance
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*
import neomu.kotlin.common.activity.BaseActivity
import neomu.kotlin.common.constanse.ValidationConstance
import neomu.kotlin.common.util.ValidationUtil.Companion.isSuccessSignInFirebaseAuth
import org.jetbrains.anko.toast

class JoinActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
     }


    fun checkAleadyJoinedAccount() {
        val result= isSuccessSignInFirebaseAuth(this, getUserEmailForJoin(), getUserPwForJoin())

        if (result) {
            addNewAccoutInFirebase()
        } else {
            toast(ValidationConstance.MSG_INVALID_PW_MATCH)
        }
    }

    fun addNewAccoutInFirebase() {
        var userInfoModel = UserInfoModel(getUserNickNameForJoin(), getUserEmailForJoin(), getUserGenderForJoin(), getUserBirthForJoin())
        val firebaseDbReference = FirebaseDatabase.getInstance().getReference()
        firebaseDbReference.child(CommonConstance.FIREBASE_DB_CULUMS_USER).setValue(userInfoModel)
    }


    private fun getUserNickNameForJoin() : String {
        val nickName = tv_join_nickName.text.toString()
        return if (nickName != null) "" else nickName
    }

    private fun getUserPwForJoin() : String {
        val password = login_pw.text.toString()
        return if(password.isNullOrEmpty()) "" else password
    }

    private fun getUserEmailForJoin() : String {
        val name = tv_join_email.text.toString()
        return if (name.isNullOrEmpty()) "" else name
    }

    private fun getUserGenderForJoin() : String {
        val selectedGender = findViewById<RadioButton>(rg_join_gender.checkedRadioButtonId).text.toString()
        return if(selectedGender.isNullOrEmpty()) "" else selectedGender
    }

    private fun getUserBirthForJoin() : String {
        val birth = picker_year.value.toString() + CommonConstance.TEXT_NUMBERPICKER_YEAR + picker_month.value.toString() + CommonConstance.TEXT_NUMBERPICKER_MONTH
        return if(birth.isNullOrEmpty()) "" else birth
    }


    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_join -> {
                //todo 유효성검사 > 기존회원인지 체크 > DB에 추가
                checkAleadyJoinedAccount()
//                ValidationUtil.isValidate(getUserEmailForJoin(), getUserNickNameForJoin(), getUserPwForJoin(), getUserPwForJoin(), object : ValidationUtil.)
            }
        }
    }


//    todo 이런 방법은 어떨까?
    fun getViewText(viewName: String): String {
        var resultText = ""
        when (viewName) {
            "mail" -> resultText = tv_join_email.text.toString()
            else -> ""
        }
        return resultText
    }
}
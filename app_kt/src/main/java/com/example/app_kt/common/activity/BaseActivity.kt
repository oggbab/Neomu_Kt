package neomu.kotlin.common.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.neomu.neomu.app.club.MainActivity
import neomu.kotlin.common.constanse.SystemMessageConstanse
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

open class BaseActivity : AppCompatActivity() {

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {


    }

}
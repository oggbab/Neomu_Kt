package neomu.kotlin.common.util

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

open class ValidationUtil {

    lateinit var istener : ValidationUtilListener

    companion object {

        fun isValidate(list : MutableList<String>, listener: ValidationUtilListener) {
//        listener = ValidationUtilListene
        }

        fun isSuccessSignInFirebaseAuth(activity: Activity, userId: String, userPw: String) : Boolean {
            val fireBaseAuth = FirebaseAuth.getInstance()
            var result = false
            fireBaseAuth.signInWithEmailAndPassword(userId, userPw)
                    .addOnCompleteListener(activity) {task ->
                        fun onComplete(task: Task<AuthResult>) {
                            result = task.isSuccessful
                        }
                    }
            return result
        }

        fun isSuccessJoinInFirebaseAuth(activity: Activity, userEmail: String, userNickName: String, userPw: String, userPw2: String) : Boolean {
            val fireBaseAuth = FirebaseAuth.getInstance()
            var result = false

            /*fireBaseAuth.signInWithEmailAndPassword(userId, userPw)
                    .addOnCompleteListener(activity) {task ->
                        fun onComplete(task: Task<AuthResult>) {
                            result = task.isSuccessful
                        }
                    }
            */
            return result
        }

    }
}
package neomu.kotlin.common.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import neomu.kotlin.common.util.FirebaseUtil
import neomu.kotlin.common.util.FirebaseUtilImpl

class BaseFragment : Fragment(), FirebaseUtilImpl {

    lateinit var mFirebaseUtil: FirebaseUtil

    override fun getFirebaseUserId(): String? {
        return mFirebaseUtil.getFirebaseUserId()
    }



}
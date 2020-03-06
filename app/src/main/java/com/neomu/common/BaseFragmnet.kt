package com.neomu.common

import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class BaseFragmnet : Fragment() {

    fun getUid(): String? {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun getQuery(databaseReference: DatabaseReference?): Query? {
        return databaseReference
    }
}
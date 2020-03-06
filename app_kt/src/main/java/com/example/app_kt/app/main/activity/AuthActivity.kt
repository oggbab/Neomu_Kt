package com.example.app_kt.app.main.activity

import android.content.Intent
import android.os.Bundle
import com.example.app_kt.R
import com.example.app_kt.common.constanse.CommonConstance
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import neomu.kotlin.common.activity.BaseActivity

class AuthActivity : BaseActivity() {

    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mFirebaseAuth = FirebaseAuth.getInstance()
        setGoogleApiConfig()
    }


    fun setGoogleApiConfig() {
        val apiOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(resources.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, apiOptions)
                .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CommonConstance.GOOGLE_SIGN_IN_API_REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result.isSuccess) {
                val account = result.signInAccount
                if (account != null) {
                    onFirebaseAuthWithGoogle(account)
                }
            }
        }
    }

    private fun onFirebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val cre
    }

}
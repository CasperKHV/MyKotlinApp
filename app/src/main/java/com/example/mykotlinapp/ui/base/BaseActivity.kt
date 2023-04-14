package com.example.mykotlinapp.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mykotlinapp.R
import com.example.mykotlinapp.data.errors.NoAuthException
import com.firebase.ui.auth.AuthUI
import com.github.ajalt.timberkt.Timber

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 42001
    }

    abstract val viewModel: BaseViewModel<T, S>
    abstract val latoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latoutRes?.let {
            setContentView(it)
        }
        viewModel.getViewState().observe(this, Observer<S> { viewState ->
            viewState?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
            }
        })

    }

    protected fun renderError(error: Throwable?) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> error?.let { t ->
                Timber.e(t)
                t.message?.let {
                    showError(it)
                }
            }
        }

    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
    }
}
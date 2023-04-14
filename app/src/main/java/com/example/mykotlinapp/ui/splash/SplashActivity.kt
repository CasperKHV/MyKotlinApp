package com.example.mykotlinapp.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlinapp.ui.base.BaseActivity
import com.example.mykotlinapp.ui.main.MainActivity
import com.example.mykotlinapp.ui.note.NoteViewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    companion object {
        private const val START_DELAY = 1000L
    }

    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val latoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser()}, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
//        if(data==true){
//            startMainActivity()
//        }

        data?.takeIf { it }?.let {
            startMainActivity() }
    }

    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }




}
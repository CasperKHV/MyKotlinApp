package com.example.mykotlinapp.ui.splash

import com.example.mykotlinapp.ui.base.BaseActivity
import com.example.mykotlinapp.ui.base.BaseViewState

class SplashViewState(isAuth: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(isAuth, error)
package com.example.mykotlinapp.ui.splash

import com.example.mykotlinapp.data.NotesRepository
import com.example.mykotlinapp.data.errors.NoAuthException
import com.example.mykotlinapp.ui.base.BaseViewModel

class SplashViewModel(private val repository: NotesRepository = NotesRepository): BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser(){
        repository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it != null){
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}
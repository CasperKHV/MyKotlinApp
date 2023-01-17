package com.example.mykotlinapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinapp.data.NotesRepository

class MainViewModel(private val repository: NotesRepository = NotesRepository) : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        repository.getNotes().observeForever{notes ->
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = notes!!) ?: MainViewState(notes!!)

        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}
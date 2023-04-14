package com.example.mykotlinapp.ui.main

import android.util.Log
import androidx.lifecycle.Observer
import com.example.mykotlinapp.data.NotesRepository
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.model.NoteResult
import com.example.mykotlinapp.ui.base.BaseViewModel

class MainViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: let { return@Observer }

        when (result) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(result.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = result.error)
            }
        }

    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}
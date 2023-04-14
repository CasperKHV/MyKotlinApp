package com.example.mykotlinapp.ui.note

import android.util.Log
import com.example.mykotlinapp.data.NotesRepository
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.model.NoteResult
import com.example.mykotlinapp.ui.base.BaseViewModel

class NoteViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { result ->
            result ?: let { return@observeForever }

            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = NoteViewState(result.data as? Note)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }
}
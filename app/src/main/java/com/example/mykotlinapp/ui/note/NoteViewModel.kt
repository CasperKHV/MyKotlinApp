package com.example.mykotlinapp.ui.note

import androidx.lifecycle.ViewModel
import com.example.mykotlinapp.data.NotesRepository
import com.example.mykotlinapp.data.entity.Note

class NoteViewModel(private val repository: NotesRepository = NotesRepository) : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note:Note){
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }

//        if (pendingNote !=null){
//            repository.saveNote(pendingNote!!)
//        }
    }
}
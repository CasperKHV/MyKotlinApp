package com.example.mykotlinapp.data.provider

import androidx.lifecycle.LiveData
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.data.entity.User
import com.example.mykotlinapp.model.NoteResult

interface RemoteDataProvider {
    fun getNoteById(id:String):LiveData<NoteResult>
    fun saveNote(note:Note):LiveData<NoteResult>
    fun subscribeToAllNotes():LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
}
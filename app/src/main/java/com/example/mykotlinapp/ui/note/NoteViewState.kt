package com.example.mykotlinapp.ui.note

import android.provider.ContactsContract
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null): BaseViewState<Note?>(note,error){
}
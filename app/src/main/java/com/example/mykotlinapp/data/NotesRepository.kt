package com.example.mykotlinapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mykotlinapp.data.entity.Note.Color
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.data.provider.FireStoreProvider
import com.example.mykotlinapp.data.provider.RemoteDataProvider
import com.example.mykotlinapp.model.NoteResult
import java.util.*

object NotesRepository {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun getNotes() = remoteDataProvider.subscribeToAllNotes()


//    private val notesLiveData = MutableLiveData<List<Note>>()
//
//    val notes: MutableList<Note> = mutableListOf(
//        Note(
//            UUID.randomUUID().toString(),
//            "Первая заметка",
//            "Текст первой заметки. Не очень длинный, но интересный",
//            color = Color.WHITE
//        ),
//        Note(
//            UUID.randomUUID().toString(),
//            "Вторая заметка",
//            "Текст второй заметки. Не очень длинный, но интересный",
//            color = Color.YELLOW
//        ),
//        Note(
//            UUID.randomUUID().toString(),
//            "Третья заметка",
//            "Текст третьей заметки. Не очень длинный, но интересный",
//            color = Color.GREEN
//        ),
//        Note(
//            UUID.randomUUID().toString(),
//            "Четвертая заметка",
//            "Текст четвертой заметки. Не очень длинный, но интересный",
//            color = Color.BLUE
//        ),
//        Note(
//            UUID.randomUUID().toString(),
//            "Пятая заметка",
//            "Текст пятой заметки. Не очень длинный, но интересный",
//            color = Color.RED
//        ),
//        Note(
//            UUID.randomUUID().toString(),
//            "Шестая заметка",
//            "Текст шестой заметки. Не очень длинный, но интересный",
//            color = Color.VIOLET
//        )
//    )
//
//    init {
//        notesLiveData.value = notes
//    }
//
//    fun getNotes(): MutableLiveData<List<Note>> {
//        return notesLiveData
//    }
//
//    fun saveNote(note: Note) {
//        addOrReplace(note)
//        notesLiveData.value = notes
//    }
//
//    fun addOrReplace(note: Note){
//        for (i in 0 until notes.size) {
//            if (notes[i] == note) {
//                notes[i] = note
//                return
//            }
//        }
//        notes.add(note)
//    }
}


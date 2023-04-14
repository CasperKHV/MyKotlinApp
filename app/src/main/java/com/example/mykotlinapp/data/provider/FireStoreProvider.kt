package com.example.mykotlinapp.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.data.entity.User
import com.example.mykotlinapp.data.errors.NoAuthException
import com.example.mykotlinapp.model.NoteResult
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val store by lazy {
        FirebaseFirestore.getInstance()
    }

    private val notesReference by lazy {
        store.collection(NOTES_COLLECTION)
    }

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener { snapshot, e ->
                value = e?.let { throw it }
                    ?: snapshot?.let {
                        val notes = it.documents.map { it.toObject(Note::class.java) }
                        NoteResult.Success(notes)
                    }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id)
                .set(note).addOnSuccessListener {
                    Timber.d { "Note $note is saved" }
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    Timber.e(it) { "Error saving note $note" }
                    throw it
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get()
                .addOnSuccessListener {
                    value = NoteResult.Success(it.toObject(Note::class.java))
                }.addOnFailureListener {
                    throw it
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

//    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
//        notesReference.document(id)
//            .get()
//            .addOnSuccessListener { snapshot ->
//                val note = snapshot.toObject(Note::class.java)
//                value = NoteResult.Success(note)
//            }.addOnFailureListener {
//                Timber.e(it) { "Error reading note with id $id" }
//                value = NoteResult.Error(it)
//            }
//    }
//
//    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
//        notesReference.document(note.id)
//            .set(note)
//            .addOnSuccessListener {
//                Timber.d { "Note $note is saved" }
//                this.value = NoteResult.Success(note)
//            }.addOnFailureListener {
//                Timber.e(it) { "Error saving note $note" }
//                value = NoteResult.Error(it)
//            }
//    }
//
//    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
//        notesReference.addSnapshotListener { snapshot, e ->
//            value = e?.let {
//                NoteResult.Error(e)
//            } ?: snapshot?.let {
//                val notes = it.documents.map { it.toObject(Note::class.java) }
//                NoteResult.Success(notes)
//            }
//        }
//    }
}
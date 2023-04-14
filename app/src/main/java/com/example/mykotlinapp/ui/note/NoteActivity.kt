package com.example.mykotlinapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlinapp.R
import com.example.mykotlinapp.common.format
import com.example.mykotlinapp.common.getColorInt
import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.ui.base.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT =
            "dd.MMM.yy HH:mm"//const val заставит компилятор подставить значение куда надо без использования переменной
        private const val SAVE_DELAY = 500L// одна секунда здесь

        fun start(context: Context, noteId: String? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            context.startActivity(intent)
        }
    }

    private var note: Note? = null
    override val latoutRes = R.layout.activity_note
    override val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    lateinit var et_title: TextInputEditText
    lateinit var et_body: EditText

    val textChangeWatcher = object : TextWatcher {
        private var timer = Timer()

        override fun afterTextChanged(p0: Editable?) {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    saveNote()
                }
            }, SAVE_DELAY)

            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.activity_note_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//делает стрелку назад на тулбаре

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        Log.d("CHECKING ERRORS:", noteId.toString())
        noteId?.let {
            viewModel.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
        }

        et_title = findViewById<TextInputEditText>(R.id.et_title)
        et_body = findViewById<EditText>(R.id.et_body)
        et_title.addTextChangedListener(textChangeWatcher)
        et_body.addTextChangedListener(textChangeWatcher)

    }

    override fun renderData(data: Note?) {
        this.note = data
//        supportActionBar?.title = if(this.note != null){
//            SimpleDateFormat (DATE_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
//        }else{
//            getString(R.string.new_note_title)
//        }

        initView()
    }

    private fun initView() {
        note?.run {
            supportActionBar?.title = lastChanged.format((DATE_FORMAT))
            et_title.setText(title)
            et_body.setText(text)
            findViewById<Toolbar>(R.id.activity_note_toolbar).setBackgroundColor(
                color.getColorInt(this@NoteActivity))
        }


    }

    private fun saveNote() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) return
        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString()
        )

        Log.d("CHECKING ERRORS SAVE:", note.toString())
        note?.let { viewModel.save(note!!) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
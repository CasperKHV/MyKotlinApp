package com.example.mykotlinapp.ui.main

import com.example.mykotlinapp.data.entity.Note
import com.example.mykotlinapp.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)
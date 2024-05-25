package com.example.shanti.presentation.home

import androidx.lifecycle.ViewModel
import com.example.shanti.data.database.SessionDatabase
import com.example.shanti.domain.repository.SessionRepository

class HomeScreenViewModel(database: SessionDatabase): ViewModel() {
    private var sessionDao = database.sessionDao()
    private val repository: SessionRepository = SessionRepository(this, sessionDao!!)
    var sessions = repository.sessions
        private set

    // Used only when Session Database is created
    fun init(){
        repository.init()
    }
}
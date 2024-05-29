package com.example.shanti.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shanti.data.database.SessionDatabase
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.domain.repository.SessionRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(database: SessionDatabase): ViewModel() {
    private var sessionDao = database.sessionDao()
    private val repository: SessionRepository = SessionRepository(this, sessionDao!!)
    var sessions = repository.sessions
        private set

    var openSessionDeleteDialog by mutableStateOf(false)

    // Used only when Session Database is created
    fun init(){
        repository.init()
    }

    fun deleteSession(sessionEntity: SessionEntity){
        viewModelScope.launch {
            repository.deleteSession(sessionEntity)
        }
    }
}
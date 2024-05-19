package com.example.shanti.domain.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.shanti.data.dao.SessionDao
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.domain.model.PractiseType
import com.example.shanti.domain.model.Status
import com.example.shanti.presentation.home.HomeScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class SessionRepository(val viewModel: HomeScreenViewModel, val dao: SessionDao) {

    var sessions = dao.getAllSessions()

    fun init(){
        viewModel.viewModelScope.launch {
            // Performing database operation off the main thread
            withContext(Dispatchers.IO){
                // Mocked values
                val list = listOf(
                    SessionEntity(
                        dateTime = Date(),
                        trainerName = "Franco",
                        trainerSurname = "Parapallo",
                        status = Status.FUTURE,
                        practiseType = PractiseType.YOGA,
                        urlMeet = "https://meet.google.com/ube-qubu-ahe"
                    ),
                    SessionEntity(
                        dateTime = Date(),
                        trainerName = "Giacomo",
                        trainerSurname = "Spuno",
                        status = Status.FUTURE,
                        practiseType = PractiseType.MEDITATION,
                        urlMeet = "https://meet.google.com/ube-qubu-ahe"
                    ),
                    SessionEntity(
                        dateTime = Date(),
                        trainerName = "Bruno",
                        trainerSurname = "Bianchetti",
                        status = Status.CANCELED,
                        practiseType = PractiseType.YOGA,
                        urlMeet = "https://meet.google.com/ube-qubu-ahe"
                    )

                )
                try {
                    dao.insertAll(list)
                } catch (e: Exception){
                    Log.e("ww", "Error inserting data: ${e.message}")
                }
            }
        }
    }

}
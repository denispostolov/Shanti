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
import com.example.shanti.common.Constants


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
                        time = "16:30",
                        trainerName = "Franco",
                        trainerSurname = "Parapallo",
                        status = Status.PASSED,
                        practiseType = PractiseType.YOGA,
                        urlMeet = Constants.URL_GOOGLE_MEET
                    ),
                    SessionEntity(
                        dateTime = Date(),
                        time = "10:00",
                        trainerName = "Giacomo",
                        trainerSurname = "Spuno",
                        status = Status.PASSED,
                        practiseType = PractiseType.MEDITATION,
                        urlMeet = Constants.URL_GOOGLE_MEET
                    ),
                    SessionEntity(
                        dateTime = Date(),
                        time = "17:45",
                        trainerName = "Bruno",
                        trainerSurname = "Bianchetti",
                        status = Status.CANCELED,
                        practiseType = PractiseType.BOTH,
                        urlMeet = Constants.URL_GOOGLE_MEET
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

    suspend fun insertSession(sessionEntity: SessionEntity){
        dao.insert(sessionEntity)
    }

}
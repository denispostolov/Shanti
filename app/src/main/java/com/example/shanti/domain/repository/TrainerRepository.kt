package com.example.shanti.domain.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.shanti.data.dao.TrainerDao
import com.example.shanti.data.entity.TrainerEntity
import com.example.shanti.domain.model.Gender
import com.example.shanti.domain.model.PractiseType
import com.example.shanti.presentation.home.book_session.BookSessionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainerRepository(val viewModel: BookSessionViewModel, val dao: TrainerDao) {
    var trainers = dao.getAll()

    fun init(){
        viewModel.viewModelScope.launch {
            // Performing database operation off the main thread
            withContext(Dispatchers.IO){
                // Mocked values
                val list = listOf(
                    TrainerEntity(
                        name = "Franco",
                        surname = "Parapallo",
                        email = "franco.parapallo@mail.com",
                        gender = Gender.MALE,
                        practiseType = PractiseType.YOGA
                    ),
                    TrainerEntity(
                        name = "Giacomo",
                        surname = "Spuno",
                        email = "giacomo.spuno@mail.com",
                        gender = Gender.MALE,
                        practiseType = PractiseType.MEDITATION
                    ),
                    TrainerEntity(
                        name = "Bruno",
                        surname = "Bianchetti",
                        email = "bruno.bianchetti@mail.com",
                        gender = Gender.MALE,
                        practiseType = PractiseType.BOTH
                    )
                )
                try {
                    list.forEach { trainerEntity ->
                        dao.insert(trainerEntity)
                    }
                } catch (e: Exception){
                    Log.e("ww", "Error inserting data: ${e.message}")
                }
            }
        }
    }

    fun getTrainersByPractiseType(practiseType: PractiseType): Flow<List<TrainerEntity>> {
        return dao.getTrainersByPractiseType(practiseType)
    }
}
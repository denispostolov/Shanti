package com.example.shanti.presentation.home.book_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shanti.data.database.TrainerDatabase
import com.example.shanti.data.entity.TrainerEntity
import com.example.shanti.domain.model.PractiseType
import com.example.shanti.domain.repository.TrainerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class BookSessionViewModel(database: TrainerDatabase): ViewModel() {
    private var trainerDao = database.trainerDao()
    private val repository: TrainerRepository = TrainerRepository(this, trainerDao!!)
    fun init(){
        repository.init()
    }

    var selectedPractiseType = MutableStateFlow<PractiseType?>(PractiseType.YOGA)
        private set
    //val selectedPractiseType: StateFlow<PractiseType?> = _selectedPractiseType

    val trainersByPractiseType: StateFlow<List<TrainerEntity>> = selectedPractiseType
        .flatMapLatest { practiseType ->
            practiseType?.let { repository.getTrainersByPractiseType(it) } ?: flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectPractiseType(practiseType: PractiseType) {
        selectedPractiseType.value = practiseType
    }
}
package com.example.shanti.presentation.home.book_session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shanti.data.database.SessionDatabase
import com.example.shanti.data.database.TrainerDatabase
import com.example.shanti.data.entity.SessionEntity
import com.example.shanti.data.entity.TrainerEntity
import com.example.shanti.domain.model.PractiseType
import com.example.shanti.domain.repository.SessionRepository
import com.example.shanti.domain.repository.TrainerRepository
import com.example.shanti.presentation.home.HomeScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookSessionViewModel(trainerDatabase: TrainerDatabase, sessionDatabase: SessionDatabase, homeScreenViewModel: HomeScreenViewModel): ViewModel() {
    private var trainerDao = trainerDatabase.trainerDao()
    private var sessionDao = sessionDatabase.sessionDao()
    private val trainerRepository: TrainerRepository = TrainerRepository(this, trainerDao!!)
    private val sessionRepository: SessionRepository = SessionRepository(homeScreenViewModel, sessionDao!!)

    // Used only when Trainer Database is created
    fun init(){
        trainerRepository.init()
    }

    fun insertSession(sessionEntity: SessionEntity){
        viewModelScope.launch {
            sessionRepository.insertSession(sessionEntity)
        }
    }

    var selectedPractiseType = MutableStateFlow<PractiseType?>(PractiseType.YOGA)
        private set
    //val selectedPractiseType: StateFlow<PractiseType?> = _selectedPractiseType

    val trainersByPractiseType: StateFlow<List<TrainerEntity>> = selectedPractiseType
        .flatMapLatest { practiseType ->
            practiseType?.let { trainerRepository.getTrainersByPractiseType(it) } ?: flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var openClearBookingDialog by mutableStateOf(false)

    fun selectPractiseType(practiseType: PractiseType) {
        selectedPractiseType.value = practiseType
    }
}
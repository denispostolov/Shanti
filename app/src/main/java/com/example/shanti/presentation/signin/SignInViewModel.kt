package com.example.shanti.presentation.signin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    var state = MutableStateFlow(SignInState())
        private set

    fun onSignInResult(result: SignInResult){
        state.update {it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )

        }
    }

    fun resetState(){
        state.update { SignInState() }
    }
}
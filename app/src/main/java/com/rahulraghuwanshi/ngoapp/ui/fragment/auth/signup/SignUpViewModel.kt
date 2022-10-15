package com.rahulraghuwanshi.ngoapp.ui.fragment.auth.signup

import androidx.lifecycle.ViewModel
import com.rahulraghuwanshi.ngoapp.data.auth.AuthListener
import com.rahulraghuwanshi.ngoapp.data.auth.AuthRepository
import com.rahulraghuwanshi.ngoapp.data.auth.FirebaseAuthSource

class SignUpViewModel(
    private val repository: AuthRepository = AuthRepository(FirebaseAuthSource())
) : ViewModel() {

    fun signUp(email: String, password: String,authListener: AuthListener){
        repository.register(email, password, authListener)
    }
}
package com.rahulraghuwanshi.ngoapp.ui.fragment.auth.login

import androidx.lifecycle.ViewModel
import com.rahulraghuwanshi.ngoapp.data.auth.AuthListener
import com.rahulraghuwanshi.ngoapp.data.auth.AuthRepository
import com.rahulraghuwanshi.ngoapp.data.auth.FirebaseAuthSource

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(FirebaseAuthSource())
) : ViewModel() {

    fun login(email: String, password: String,authListener: AuthListener){
        //validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        if (password?.length!! < 6) {
            authListener?.onFailure("Password length should not less than 6")
            return
        }

        repository.login(email,password,authListener)
    }
}
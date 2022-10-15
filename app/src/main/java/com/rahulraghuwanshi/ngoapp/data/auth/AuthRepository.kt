package com.rahulraghuwanshi.ngoapp.data.auth

class AuthRepository (
    private val firebase: FirebaseAuthSource
) {

    fun login(email: String, password: String,authListener: AuthListener) = firebase.login(email, password,authListener)

    fun register(email: String, password: String,authListener: AuthListener) = firebase.register(email, password,authListener)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}
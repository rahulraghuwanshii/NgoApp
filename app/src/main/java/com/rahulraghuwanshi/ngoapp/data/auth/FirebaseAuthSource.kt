package com.rahulraghuwanshi.ngoapp.data.auth

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthSource {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String,authListener: AuthListener) {
        authListener.onStarted()
        firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    authListener.onSuccess()
                }else{
                    authListener.onFailure(it.exception?.message.toString())
                }
            }
            .addOnFailureListener{
                authListener.onFailure(it.message.toString())
            }

    }



    fun register(email: String, password: String,authListener: AuthListener) {
        authListener.onStarted()
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    authListener.onSuccess()
                }else{
                    authListener.onFailure(it.exception?.message.toString())
                }
            }
            .addOnFailureListener{
                authListener.onFailure(it.message.toString())
            }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}
package com.rahulraghuwanshi.ngoapp.data.donation

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse

class DonationRepository (
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val donationRef: DatabaseReference = rootRef.child("Donation")
        ){
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: DonationFirebaseCallback) {
        donationRef.get().addOnCompleteListener { task ->
            val response = DonationResponse()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.donation = result.children.map { snapShot ->
                        snapShot.getValue(Donation::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            callback.onResponse(response)
        }
    }
}
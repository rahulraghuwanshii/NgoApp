package com.rahulraghuwanshi.ngoapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rahulraghuwanshi.ngoapp.data.FirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.Post
import com.rahulraghuwanshi.ngoapp.data.Response

class PostRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val postRef: DatabaseReference = rootRef.child("Posts")
) {
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: FirebaseCallback) {
        postRef.get().addOnCompleteListener { task ->
            val response = Response()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.post = result.children.map { snapShot ->
                        snapShot.getValue(Post::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            callback.onResponse(response)
        }
    }

    fun post(post: Post, callback: FirebaseCallback) {
        val response = Response()
        postRef.child(post.id.toString()).setValue(post)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.let {
                        response.post = null
                    }
                } else {
                    response.exception = task.exception
                }
                callback.onResponse(response)
            }
            .addOnFailureListener {
                response.exception = it
                callback.onResponse(response)
            }
    }
}
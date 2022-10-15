package com.rahulraghuwanshi.ngoapp.data.post

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PostRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val postRef: DatabaseReference = rootRef.child("Posts")
) {
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: PostFirebaseCallback) {
        postRef.get().addOnCompleteListener { task ->
            val response = PostResponse()
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

    fun post(post: Post, callback: PostFirebaseCallback) {
        val response = PostResponse()
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
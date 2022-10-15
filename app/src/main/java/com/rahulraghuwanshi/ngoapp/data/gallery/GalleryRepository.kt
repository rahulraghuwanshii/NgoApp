package com.rahulraghuwanshi.ngoapp.data.gallery

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse

class GalleryRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val galleryRef: DatabaseReference = rootRef.child("Gallery")
) {
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: GalleryFirebaseCallback) {
        galleryRef.get().addOnCompleteListener { task ->
            val response = GalleryResponse()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.gallery = result.children.map { snapShot ->
                        snapShot.getValue(Gallery::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            callback.onResponse(response)
        }
    }

    fun gallery(gallery: Gallery, callback: GalleryFirebaseCallback) {
        val response = GalleryResponse()
        galleryRef.child(gallery.id.toString()).setValue(gallery)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.let {
                        response.gallery = null
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
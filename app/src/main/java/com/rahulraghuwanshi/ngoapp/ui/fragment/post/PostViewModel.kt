package com.rahulraghuwanshi.ngoapp.ui.fragment.post

import androidx.lifecycle.ViewModel
import com.rahulraghuwanshi.ngoapp.data.FirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.Post
import com.rahulraghuwanshi.ngoapp.repository.PostRepository

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    fun getResponseUsingCallback(callback: FirebaseCallback) =
        repository.getResponseFromRealtimeDatabaseUsingCallback(callback)

    fun post(post: Post, callback: FirebaseCallback) = repository.post(post, callback)
}
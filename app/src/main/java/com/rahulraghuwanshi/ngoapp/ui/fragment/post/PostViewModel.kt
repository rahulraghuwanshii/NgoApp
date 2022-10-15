package com.rahulraghuwanshi.ngoapp.ui.fragment.post

import androidx.lifecycle.ViewModel
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostRepository

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    fun getResponseUsingCallback(callback: PostFirebaseCallback) =
        repository.getResponseFromRealtimeDatabaseUsingCallback(callback)

    fun post(post: Post, callback: PostFirebaseCallback) = repository.post(post, callback)
}
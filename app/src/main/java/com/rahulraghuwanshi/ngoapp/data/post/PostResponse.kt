package com.rahulraghuwanshi.ngoapp.data.post

import com.rahulraghuwanshi.ngoapp.data.post.Post

data class PostResponse(
    var post: List<Post>? = null,
    var exception: Exception? = null
)
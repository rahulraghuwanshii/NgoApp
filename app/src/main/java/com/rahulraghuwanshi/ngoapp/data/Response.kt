package com.rahulraghuwanshi.ngoapp.data

data class Response(
    var post: List<Post>? = null,
    var exception: Exception? = null
)
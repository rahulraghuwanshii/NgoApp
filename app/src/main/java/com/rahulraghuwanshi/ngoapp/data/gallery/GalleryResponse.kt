package com.rahulraghuwanshi.ngoapp.data.gallery

import com.rahulraghuwanshi.ngoapp.data.post.Post

data class GalleryResponse(
    var gallery : List<Gallery>? = null,
    var exception: Exception? = null
)
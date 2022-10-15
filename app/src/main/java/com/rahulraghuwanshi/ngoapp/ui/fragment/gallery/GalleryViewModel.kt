package com.rahulraghuwanshi.ngoapp.ui.fragment.gallery

import androidx.lifecycle.ViewModel
import com.rahulraghuwanshi.ngoapp.data.gallery.Gallery
import com.rahulraghuwanshi.ngoapp.data.gallery.GalleryFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.gallery.GalleryRepository
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback

class GalleryViewModel(
    private val repository: GalleryRepository = GalleryRepository()
) : ViewModel() {
    fun getResponseUsingCallback(callback: GalleryFirebaseCallback) =
        repository.getResponseFromRealtimeDatabaseUsingCallback(callback)

    fun gallery(gallery: Gallery, callback: GalleryFirebaseCallback) = repository.gallery(gallery, callback)
}
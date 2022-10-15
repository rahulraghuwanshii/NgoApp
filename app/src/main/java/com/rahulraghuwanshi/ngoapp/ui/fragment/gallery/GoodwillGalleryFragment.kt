package com.rahulraghuwanshi.ngoapp.ui.fragment.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.data.gallery.Gallery
import com.rahulraghuwanshi.ngoapp.data.gallery.GalleryFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.gallery.GalleryResponse
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse
import com.rahulraghuwanshi.ngoapp.databinding.FragmentGoodwillGalleryBinding
import com.rahulraghuwanshi.ngoapp.databinding.FragmentSignUpBinding
import com.rahulraghuwanshi.ngoapp.ui.adapter.GalleryAdapter
import com.rahulraghuwanshi.ngoapp.ui.adapter.PostAdapter
import com.rahulraghuwanshi.ngoapp.ui.fragment.post.PostViewModel

class GoodwillGalleryFragment : Fragment() {

    private var _binding: FragmentGoodwillGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoodwillGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        //actions are here
    }
    private fun getPost() {
        viewModel.getResponseUsingCallback(object : GalleryFirebaseCallback {
            override fun onResponse(response: GalleryResponse) {
                response.gallery?.let { gallery ->
                    //here we set our recyclerview
                    binding.apply {
                        rvGallery.adapter = GalleryAdapter(gallery)
                    }
                }

                response.exception?.let { exception ->
                    exception.message?.let {
                        Toast.makeText(context, "Something is wrong!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        });
    }

    private fun addGallery(gallery: Gallery) {
        viewModel.gallery(gallery, object : GalleryFirebaseCallback {
            override fun onResponse(response: GalleryResponse) {
                response.gallery?.let { gallery ->
                    //here we call as our data is save
                    Toast.makeText(context, "Post Uploaded", Toast.LENGTH_SHORT).show()
                }

                response.exception?.let { exception ->
                    exception.message?.let {
                        Toast.makeText(context, "Something is wrong!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.rahulraghuwanshi.ngoapp.ui.fragment.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.rahulraghuwanshi.ngoapp.utils.Utils

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

        getGallery()
    }
    private fun getGallery() {
        val progress = context?.let { Utils.progressDialog(it) }
        viewModel.getResponseUsingCallback(object : GalleryFirebaseCallback {
            override fun onResponse(response: GalleryResponse) {
                response.gallery?.let { gallery ->
                    progress?.dismiss()
                    //here we set our recyclerview
                    binding.apply {
                        binding.apply {
                            rvGallery.layoutManager = GridLayoutManager(context,2)
                            rvGallery.adapter = GalleryAdapter(gallery)
                        }
                    }
                }

                response.exception?.let { exception ->
                    exception.message?.let {
                        progress?.dismiss()
                        Toast.makeText(context, "Something is wrong!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        });
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
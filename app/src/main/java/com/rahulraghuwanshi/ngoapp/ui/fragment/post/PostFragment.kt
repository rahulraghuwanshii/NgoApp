package com.rahulraghuwanshi.ngoapp.ui.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse
import com.rahulraghuwanshi.ngoapp.databinding.FragmentPostBinding


class PostFragment : Fragment() {

    private val TAG = "POSTFRAGMENT"

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        //actions are here

    }

    private fun getPost() {
        viewModel.getResponseUsingCallback(object : PostFirebaseCallback {
            override fun onResponse(response: PostResponse) {
                response.post?.let { post ->
                    //here we set our recyclerview
                }

                response.exception?.let { exception ->
                    exception.message?.let {
                        Toast.makeText(context, "Something is wrong!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        });
    }

    private fun post(post: Post) {
        viewModel.post(post, object : PostFirebaseCallback {
            override fun onResponse(response: PostResponse) {
                response.post?.let { post ->
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
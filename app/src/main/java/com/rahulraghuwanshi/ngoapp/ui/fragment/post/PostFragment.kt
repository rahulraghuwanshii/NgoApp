package com.rahulraghuwanshi.ngoapp.ui.fragment.post

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse
import com.rahulraghuwanshi.ngoapp.databinding.FragmentPostBinding
import com.rahulraghuwanshi.ngoapp.ui.adapter.PostAdapter
import com.rahulraghuwanshi.ngoapp.utils.Utils
import java.text.SimpleDateFormat
import java.util.*


class PostFragment : Fragment() {

    private val TAG = "POSTFRAGMENT"

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPost.setOnClickListener {
            showPostDialog(requireContext())
        }
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        //actions are here

    }
    private fun showPostDialog(activity: Context) {

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_post)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        val btnAddPost = dialog.findViewById<AppCompatButton>(R.id.btn_add_post)
        val etPostTitle = dialog.findViewById<AppCompatButton>(R.id.et_post_title)
        val etNgoName = dialog.findViewById<AppCompatButton>(R.id.et_ngo_name)
        val etNgoDesc = dialog.findViewById<AppCompatButton>(R.id.et_ngo_desc)
        val ivCancel = dialog.findViewById<AppCompatButton>(R.id.iv_cancel)

        val id = UUID.randomUUID().toString()
        val title = etPostTitle.text.toString()
        val ngoName = etNgoName.text.toString()
        val ngoDesc = etNgoDesc.text.toString()
        val simpleDate = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
        val currentDate = simpleDate.format(Date()).toString()

        val post : Post = Post(id,ngoName,title,ngoDesc,currentDate)
        ivCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnAddPost.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun getPost() {
        viewModel.getResponseUsingCallback(object : PostFirebaseCallback {
            override fun onResponse(response: PostResponse) {
                response.post?.let { post ->
                    //here we set our recyclerview
                    binding.apply {
                        rvPost.adapter = PostAdapter(post)
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
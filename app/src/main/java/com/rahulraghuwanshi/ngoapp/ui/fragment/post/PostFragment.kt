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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
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

        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        //actions are here
        checkAuthUser()

        binding.btnPost.setOnClickListener {
            showPostDialog(requireContext())
        }
    }

    private fun checkAuthUser() {
        if (FirebaseAuth.getInstance().currentUser == null){
            findNavController(binding.root).navigate(
                PostFragmentDirections.actionPostFragmentToLoginFragment())
        }else{
            getPost()
        }
    }

    private fun showPostDialog(context: Context) {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_post)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        val btnAddPost = dialog.findViewById<Button>(R.id.btn_add_post)
        val etPostTitle = dialog.findViewById<EditText>(R.id.et_post_title)
        val etNgoName = dialog.findViewById<EditText>(R.id.et_ngo_name)
        val etNgoDesc = dialog.findViewById<EditText>(R.id.et_ngo_desc)
        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)

        ivCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnAddPost.setOnClickListener {

            val id = UUID.randomUUID().toString()
            val title = etPostTitle.text.toString()
            val ngoName = etNgoName.text.toString()
            val ngoDesc = etNgoDesc.text.toString()
            val simpleDate = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
            val currentDate = simpleDate.format(Date()).toString()

            val post = Post(id,ngoName,title,ngoDesc,currentDate)
            post(post)
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun getPost() {
        val progress = context?.let { Utils.progressDialog(it) }
        viewModel.getResponseUsingCallback(object : PostFirebaseCallback {
            override fun onResponse(response: PostResponse) {
                response.post?.let { post ->
                    //here we set our recyclerview
                    progress?.dismiss()
                    binding.apply {
                        rvPost.layoutManager = LinearLayoutManager(context)
                        rvPost.adapter = PostAdapter(post)
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

    private fun post(post: Post) {
        val progress = context?.let { Utils.progressDialog(it) }
        viewModel.post(post, object : PostFirebaseCallback {
            override fun onResponse(response: PostResponse) {
                response.post.let { post ->
                    //here we call as our data is save
                    progress?.dismiss()
                    Toast.makeText(context, "Post Uploaded", Toast.LENGTH_SHORT).show()
                }

                response.exception?.let { exception ->
                    exception.message?.let {
                        progress?.dismiss()
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
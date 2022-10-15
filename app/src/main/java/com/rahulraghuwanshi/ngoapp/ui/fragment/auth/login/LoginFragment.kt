package com.rahulraghuwanshi.ngoapp.ui.fragment.auth.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.data.auth.AuthListener
import com.rahulraghuwanshi.ngoapp.databinding.FragmentDonationBinding
import com.rahulraghuwanshi.ngoapp.databinding.FragmentLoginBinding
import com.rahulraghuwanshi.ngoapp.ui.fragment.post.PostFragmentDirections
import com.rahulraghuwanshi.ngoapp.ui.fragment.post.PostViewModel
import com.rahulraghuwanshi.ngoapp.utils.Utils

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.apply {
            btnLogin.setOnClickListener {
                login()
            }

            txtRegister.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(
                    LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
        }
    }

    private fun login() {
        val dialog = context?.let { Utils.progressDialog(it) }
        binding.apply {
        if (etEmail.text.isNullOrEmpty() || !etEmail.text.toString().isValidEmail() ){
            etEmail.error = "Please Enter Valid email"
            dialog?.dismiss()
            return
        }
        if (etPassword.text.isNullOrEmpty()){
            etPassword.error = "Please enter password"
            dialog?.dismiss()
            return
        }
            if (etPassword.text.length < 6) {
                etPassword.error = "Password length should not less than 6"
                dialog?.dismiss()
                return
            }
        viewModel.login(etEmail.text.toString(),etPassword.text.toString(),object : AuthListener{
            override fun onStarted() {
            }

            override fun onSuccess() {
                Navigation.findNavController(binding.root).navigate(
                    LoginFragmentDirections.actionLoginFragmentToPostFragment())
                dialog?.dismiss()
            }

            override fun onFailure(message: String) {
                dialog?.dismiss()
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
            }
        })
        }
    }

    fun String?.isValidEmail(): Boolean =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
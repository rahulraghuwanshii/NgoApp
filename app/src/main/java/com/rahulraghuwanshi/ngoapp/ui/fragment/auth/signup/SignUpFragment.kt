package com.rahulraghuwanshi.ngoapp.ui.fragment.auth.signup

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.data.auth.AuthListener
import com.rahulraghuwanshi.ngoapp.databinding.FragmentDonationBinding
import com.rahulraghuwanshi.ngoapp.databinding.FragmentSignUpBinding
import com.rahulraghuwanshi.ngoapp.ui.fragment.auth.login.LoginFragmentDirections
import com.rahulraghuwanshi.ngoapp.ui.fragment.auth.login.LoginViewModel
import com.rahulraghuwanshi.ngoapp.utils.Utils

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.apply {
            btnRegister.setOnClickListener {
                signUp()
            }
            txtLogin.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
            }
        }
    }

    private fun signUp() {
        val dialog = context?.let { Utils.progressDialog(it) }
        binding.apply {
        if (etFullName.text.isNullOrEmpty()) {
            etFullName.error = "Enter Valid Detail"
            dialog?.dismiss()
            return
        }
            if (etEmail.text.isNullOrEmpty() || !etEmail.text.toString().isValidEmail()) {
                etEmail.error = "Enter Valid Detail"
                dialog?.dismiss()
                return
            }
        if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
            etPassword.error = "Password not match"
            etConfirmPassword.error = "Password not match"
            dialog?.dismiss()
            return
        }
        if (etPassword.text.toString().length < 6 ||etEmail.text.toString().length < 6) {
            etPassword.error = "Password length greater than 6"
            etConfirmPassword.error = "Password length greater than 6"
            dialog?.dismiss()
            return
        }

            viewModel.signUp(
                etEmail.text.toString(),
                etPassword.text.toString(),
                object : AuthListener{
                    override fun onStarted() {
                    }

                    override fun onSuccess() {
                        Navigation.findNavController(binding.root).navigate(
                            SignUpFragmentDirections.actionSignUpFragmentToPostFragment())
                        dialog?.dismiss()
                    }

                    override fun onFailure(message: String) {
                        dialog?.dismiss()
                        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                    }

                }
            )
        }
    }

    fun String?.isValidEmail(): Boolean =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
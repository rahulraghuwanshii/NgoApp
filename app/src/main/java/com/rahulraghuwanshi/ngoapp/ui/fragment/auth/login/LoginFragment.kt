package com.rahulraghuwanshi.ngoapp.ui.fragment.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.databinding.FragmentDonationBinding
import com.rahulraghuwanshi.ngoapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //actions are here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.rahulraghuwanshi.ngoapp.ui.fragment.donation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rahulraghuwanshi.ngoapp.data.donation.Donation
import com.rahulraghuwanshi.ngoapp.data.donation.DonationFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.donation.DonationResponse
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse
import com.rahulraghuwanshi.ngoapp.databinding.FragmentDonationBinding
import com.rahulraghuwanshi.ngoapp.ui.adapter.DonateAdapter
import com.rahulraghuwanshi.ngoapp.ui.adapter.PostAdapter

class DonationFragment : Fragment() {
    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DonationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DonationViewModel::class.java]
        //actions are here
    }
    private fun getDonationInfo() {
        viewModel.getResponseUsingCallback(object : DonationFirebaseCallback {
            override fun onResponse(response: DonationResponse) {
                response.donation?.let { donation ->
                    //here we set our recyclerview
                    binding.apply {
                        rvDonate.adapter = DonateAdapter(donation)
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
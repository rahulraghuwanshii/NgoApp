package com.rahulraghuwanshi.ngoapp.ui.fragment.donation

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
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.data.donation.Donation
import com.rahulraghuwanshi.ngoapp.data.donation.DonationFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.donation.DonationResponse
import com.rahulraghuwanshi.ngoapp.data.post.Post
import com.rahulraghuwanshi.ngoapp.data.post.PostFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.post.PostResponse
import com.rahulraghuwanshi.ngoapp.databinding.FragmentDonationBinding
import com.rahulraghuwanshi.ngoapp.ui.adapter.DonateAdapter
import com.rahulraghuwanshi.ngoapp.ui.adapter.PostAdapter
import com.rahulraghuwanshi.ngoapp.ui.adapter.itemClickListener
import com.rahulraghuwanshi.ngoapp.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

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
        getDonationInfo()
    }

    private fun getDonationInfo() {
        val progress = context?.let { Utils.progressDialog(it) }
        viewModel.getResponseUsingCallback(object : DonationFirebaseCallback {
            override fun onResponse(response: DonationResponse) {
                response.donation?.let { donation ->
                    progress?.dismiss()
                    //here we set our recyclerview
                    binding.apply {
                        rvDonate.layoutManager = LinearLayoutManager(context)
                        rvDonate.adapter = DonateAdapter(donation, object : itemClickListener {
                            override fun onItemClick(donation: Donation) {
                                context?.let { showDialog(donation, it) }
                            }
                        })
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

    private fun showDialog(donation: Donation, context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_donation)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );

        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)

        ivCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
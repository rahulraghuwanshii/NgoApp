package com.rahulraghuwanshi.ngoapp.ui.fragment.donation

import androidx.lifecycle.ViewModel
import com.rahulraghuwanshi.ngoapp.data.donation.Donation
import com.rahulraghuwanshi.ngoapp.data.donation.DonationFirebaseCallback
import com.rahulraghuwanshi.ngoapp.data.donation.DonationRepository

class DonationViewModel(
    private val repository: DonationRepository =  DonationRepository()
) : ViewModel() {
    fun getResponseUsingCallback(callback: DonationFirebaseCallback) =
        repository.getResponseFromRealtimeDatabaseUsingCallback(callback)
}
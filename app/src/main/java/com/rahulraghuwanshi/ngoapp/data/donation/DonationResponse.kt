package com.rahulraghuwanshi.ngoapp.data.donation

import com.rahulraghuwanshi.ngoapp.data.post.Post

data class DonationResponse(
    var donation : List<Donation>? = null,
    var exception: Exception? = null
)
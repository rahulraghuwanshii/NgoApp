package com.rahulraghuwanshi.ngoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.ngoapp.data.donation.Donation
import com.rahulraghuwanshi.ngoapp.databinding.LayoutDonateBinding
import com.rahulraghuwanshi.ngoapp.databinding.LayoutPostBinding


class DonateAdapter(
    private val donationList: List<Donation>,
) : RecyclerView.Adapter<DonateAdapter.DonateViewHolder>() {

    inner class DonateViewHolder(val binding: LayoutDonateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonateViewHolder {
        val binding = LayoutDonateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonateViewHolder, position: Int) {
        val donation = donationList[position]
        holder.binding.apply {
            txtNgoName.text = donation.ngo_name
            txtMoto.text = donation.moto
        }
    }

    override fun getItemCount(): Int = donationList.size;
}
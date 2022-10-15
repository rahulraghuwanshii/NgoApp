package com.rahulraghuwanshi.ngoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.rahulraghuwanshi.ngoapp.databinding.LayoutPostBinding


class DonateAdapter(
   // private val workshops: List<Post>,
) : RecyclerView.Adapter<DonateAdapter.DonateViewHolder>() {

    inner class DonateViewHolder(private val binding: LayoutPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonateViewHolder {
        val binding = LayoutPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonateViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 0;
}
package com.rahulraghuwanshi.ngoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulraghuwanshi.ngoapp.data.gallery.Gallery
import com.rahulraghuwanshi.ngoapp.databinding.LayoutGalleryBinding
import com.rahulraghuwanshi.ngoapp.databinding.LayoutPostBinding


class GalleryAdapter(
    private val galleryList: List<Gallery>,
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(val binding: LayoutGalleryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = LayoutGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val gallery = galleryList[position]
        holder.binding.apply {
            txtNgoName.text = gallery.ngo_name
        }
        Glide.with(holder.itemView.context)
            .load(gallery.img_url)
            .into(holder.binding.imgGallery)
    }

    override fun getItemCount(): Int = galleryList.size;
}
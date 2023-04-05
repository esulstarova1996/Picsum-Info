package com.impaladigital.ui_photolist.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.impaladigital.photo_domain.PhotoData

class PhotoItemDiffCallback : DiffUtil.ItemCallback<PhotoData>() {

    override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
        return oldItem.download_url == newItem.download_url && oldItem.author == newItem.author
    }

}
package com.impaladigital.ui_photolist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.ui_photolist.R
import com.impaladigital.ui_photolist.databinding.ImageDataItemBinding

internal class PhotoListAdapter(
    private var photoItemClick: (photoId: String) -> Unit,
) : ListAdapter<PhotoData, PhotoListAdapter.PhotoItemViewHolder>(PhotoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        return PhotoItemViewHolder.from(parent, photoItemClick)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.bind(photoData = getItem(position))
    }

    class PhotoItemViewHolder(
        private val binding: ImageDataItemBinding,
        private var photoItemClick: (photoId: String) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photoData: PhotoData) {
            binding.authorTv.text = photoData.author

            binding.contentIv.load(photoData.download_url) {
                placeholder(R.drawable.white_background)
                error(R.drawable.error_image)
            }

            binding.root.setOnClickListener {
                photoItemClick(photoData.id)
            }

        }

        companion object {
            fun from(
                parent: ViewGroup,
                photoItemClick: (photoId: String) -> Unit,
            ): PhotoItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImageDataItemBinding.inflate(layoutInflater, parent, false)
                return PhotoItemViewHolder(binding, photoItemClick)
            }
        }
    }
}
package com.impaladigital.ui_photodetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.impaladigital.photo_interactors.domain.ProgressBarState
import com.impaladigital.photo_interactors.domain.UiComponent
import com.impaladigital.ui_photodetails.R
import com.impaladigital.ui_photodetails.databinding.FragmentPhotoDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private val photoDetailViewModel: PhotoDetailViewModel by viewModels()

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding: FragmentPhotoDetailBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeUiEvents()
    }

    private fun initView() {

        binding.grayscaleToggleBtn.setOnCheckedChangeListener { _, isChecked ->
            photoDetailViewModel.onTriggerEvent(PhotoDetailEvent.ApplyFilter(isGrayscale = isChecked,
                binding.seekBar.progress))
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                photoDetailViewModel.onTriggerEvent(
                    PhotoDetailEvent.ApplyFilter(binding.grayscaleToggleBtn.isChecked, progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }


    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoDetailViewModel.uiState.collect { state ->

                    with(state) {

                        if (this.progressBarState == ProgressBarState.Loading)
                            View.VISIBLE
                        else
                            View.GONE

                        this.photoUrl.takeIf { it.isNotEmpty() }?.run {
                            binding.detailIv.load(this) {
                                placeholder(R.drawable.loading)
                                error(R.drawable.error_image)
                            }
                        }

                        this.author.takeIf { it.isNotEmpty() }?.run {
                            binding.authorTv.text = this
                        }

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoDetailViewModel.messageChannel.collect { component ->
                    if (component is UiComponent.Dialog) {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(component.title)
                            .setMessage(component.description)
                            .setPositiveButton(getString(R.string.retry)) { _, _ ->
                                photoDetailViewModel.onTriggerEvent(PhotoDetailEvent.GetPhotoDetails(
                                    photoDetailViewModel.photoId ?: "-1"))
                            }
                            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                            .show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.impaladigital.ui_photolist.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.impaladigital.photo_interactors.domain.ProgressBarState
import com.impaladigital.photo_interactors.domain.UiComponent
import com.impaladigital.ui_photolist.R
import com.impaladigital.ui_photolist.databinding.FragmentPhotoListBinding
import com.impaladigital.ui_photolist.ui.adapter.PhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoListFragment : Fragment(R.layout.fragment_photo_list) {

    private val photoListViewModel: PhotoListViewModel by viewModels()

    private var _binding: FragmentPhotoListBinding? = null
    private val binding: FragmentPhotoListBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeUiEvents()
    }

    private fun initView() {
        binding.photoListRv.apply {

            adapter = PhotoListAdapter { photoId ->
                val uri = Uri.parse("Picsum://PhotoDetailFragment/$photoId")
                findNavController().navigate(uri)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if ((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == photoListViewModel.uiState.value.photos.size - 1) {
                        photoListViewModel.onTriggerEvent(PhotoListEvents.GetPhotoNextPage)
                    }

                }

            })

            setHasFixedSize(true)
        }
    }

    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoListViewModel.uiState.collect { state ->

                    with(state) {

                        binding.syncPb.visibility =
                            if (this.progressBarState == ProgressBarState.Loading)
                                View.VISIBLE
                            else
                                View.GONE

                        state.photos.takeUnless {
                            it.isNullOrEmpty()
                        }?.run {
                            (binding.photoListRv.adapter as PhotoListAdapter).submitList(this)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoListViewModel.messageChannel.collect { component ->
                    if (component is UiComponent.Dialog) {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(component.title)
                            .setMessage(component.description)
                            .setPositiveButton("Retry") { _, _ ->
                                photoListViewModel.onTriggerEvent(PhotoListEvents.GetPhotoNextPage)
                            }
                            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
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
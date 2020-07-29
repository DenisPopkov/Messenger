package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentZoomImagesBinding
import ru.popkovden.messengerapplication.ui.adapters.zoom.ZoomImageViewPager

class ZoomImagesFragment : Fragment() {

    private lateinit var binding: FragmentZoomImagesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zoom_images, container, false)

        arguments?.let {
            val imageList = ZoomImagesFragmentArgs.fromBundle(it).imagesList
            val position = ZoomImagesFragmentArgs.fromBundle(it).position
            binding.zoomImageViewPager.adapter = ZoomImageViewPager(imageList.toMutableList(), requireContext())
            binding.zoomImageViewPager.currentItem = position
        }

        return binding.root
    }
}
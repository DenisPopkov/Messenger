package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentFeedScreenBinding
import ru.popkovden.messengerapplication.utils.helper.getData.setPhotoCount
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class FeedScreenFragment : Fragment() {

    private lateinit var binding: FragmentFeedScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_screen, container, false)

        InfoAboutUser.loadInfoFromSharedPreferences(requireContext())

        if (InfoAboutUser.setPhotoCount) {
            setPhotoCount(InfoAboutUser.UID)
            InfoAboutUser.setPhotoCount = false
        }

        return binding.root
    }
}
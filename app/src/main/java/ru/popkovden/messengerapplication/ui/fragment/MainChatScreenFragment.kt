package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.koin.android.ext.android.bind
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentMainChatScreenBinding
import ru.popkovden.messengerapplication.ui.adapters.MainChatScreenViewPagerAdapter

class MainChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainChatScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chat_screen, container, false)

        binding.mainChatScreenViewPager.adapter = MainChatScreenViewPagerAdapter(this)

//        val count = binding.mainChatScreenViewPager.adapter!!.itemCount
//        if (count == 2) {
//            binding.mainChatBottomNavigationView.tint
//        }

        return binding.root
    }
}
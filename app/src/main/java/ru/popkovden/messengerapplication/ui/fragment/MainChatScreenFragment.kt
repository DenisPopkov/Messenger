package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentMainChatScreenBinding

class MainChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainChatScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chat_screen, container, false)

//        val count = binding.mainChatScreenViewPager.adapter!!.itemCount
//        if (count == 2) {
//            binding.mainChatBottomNavigationView.tint
//        }

        binding.mainChatBottomNavigationView.setOnNavigationItemSelectedListener {menuItem ->
            when (menuItem.itemId) {
                R.id.discover -> {
                    val fragment = FeedScreenFragment()
                    childFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.chat -> {
                    val fragment = ChatScreenFragment()
                    childFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.account -> {
                    val fragment = UserProfileFragment()
                    childFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> true
            }
        }

        return binding.root
    }
}
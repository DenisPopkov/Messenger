package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentMainChatScreenBinding

class MainChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainChatScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chat_screen, container, false)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_test) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainChatBottomNavigationView, navController)

        return binding.root
    }
}
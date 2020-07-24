package ru.popkovden.messengerapplication.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentMainChatScreenBinding
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.darkMode.openDarkMode
import ru.popkovden.messengerapplication.utils.helper.setOnlineStatus
import ru.popkovden.messengerapplication.utils.helper.updateScreenDestination
import ru.popkovden.messengerapplication.utils.internetChecker.CheckInternetConnection

class MainChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainChatScreenBinding
    private val uiHelper: StatusBarColorChanger by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chat_screen, container, false)

        openDarkMode(requireContext())

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_test) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainChatBottomNavigationView, navController)

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.messenger -> {
                    binding.mainChatBottomNavigationView.visibility = View.GONE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (CheckInternetConnection.isOnline(requireContext())) {
                            setOnlineStatus("online")
                        }
                    }
                }

                R.id.discover, R.id.chat -> {

                    binding.mainChatBottomNavigationView.visibility = View.VISIBLE

                    updateScreenDestination("", "")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (CheckInternetConnection.isOnline(requireContext())) {
                            setOnlineStatus("online")
                        }
                    }
                }

                R.id.createPost,
                R.id.editProfileFragment,
                R.id.zoomImagesFragment,
                R.id.contactsFragment -> {
                    binding.mainChatBottomNavigationView.visibility = View.GONE
                    updateScreenDestination("", "")
                }

                else -> {
                    updateScreenDestination("", "")
                    binding.mainChatBottomNavigationView.visibility = View.VISIBLE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (CheckInternetConnection.isOnline(requireContext())) {
                            setOnlineStatus("online")
                        } else {
                            setOnlineStatus("offline")
                        }
                    }
                }
            }
        }

        return binding.root
    }
}
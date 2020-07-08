package ru.popkovden.messengerapplication.ui.fragment

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

class MainChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainChatScreenBinding
    private val uiHelper: StatusBarColorChanger by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chat_screen, container, false)
        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_test) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainChatBottomNavigationView, navController)

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.messenger,
                R.id.createPost,
                R.id.editProfileFragment,
                R.id.zoomImagesFragment,
                R.id.contactsFragment -> binding.mainChatBottomNavigationView.visibility = View.GONE
                else -> binding.mainChatBottomNavigationView.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}
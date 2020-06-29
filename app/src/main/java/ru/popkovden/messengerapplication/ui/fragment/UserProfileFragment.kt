package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentUserProfileBinding
import ru.popkovden.messengerapplication.ui.adapters.profile.MainProfileRecyclerViewPart
import ru.popkovden.messengerapplication.ui.adapters.profile.PostsProfileRecyclerView
import ru.popkovden.messengerapplication.utils.custom_view.FabControl
import ru.popkovden.messengerapplication.utils.custom_view.StatusBarColorChanger

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val uiHelper: StatusBarColorChanger by inject()
    private val fabControl: FabControl by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        val mergerAdapter = MergeAdapter(MainProfileRecyclerViewPart(requireContext()), PostsProfileRecyclerView(requireContext(), arrayListOf()))
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRecyclerView.adapter = mergerAdapter

        // Настройка интерфейса
        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)
        fabControl.controlFabActionPosition(binding.profileRecyclerView, binding.fab)
        drawerControl(binding.drawerLayout, binding.content)

        binding.fab.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionAccountToCreatePostFragmentFragment())
        }

        return binding.root
    }

    private fun drawerControl(drawerLayout: DrawerLayout, contentLayout: ConstraintLayout) {
        val actionBarDrawerToggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                requireActivity(),
                drawerLayout,
                R.string.app_name,
                R.string.about
            ) {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)

                    val slideX = drawerView.width * slideOffset
                    contentLayout.translationX = -slideX
                }
            }

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
    }
}
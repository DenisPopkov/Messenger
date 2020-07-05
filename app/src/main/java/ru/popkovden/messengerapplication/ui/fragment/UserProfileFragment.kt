package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.profile_toolbar.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.databinding.FragmentUserProfileBinding
import ru.popkovden.messengerapplication.utils.customView.FabControl
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.UserProfileFragmentViewModel

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val uiHelper: StatusBarColorChanger by inject()
    private val fabControl: FabControl by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private val getPostsHelper: GetPosts by inject()
    private val viewModel: UserProfileFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentUserProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение информации о профиле пользователя
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())

        getPostsHelper.getPosts(infoAboutUser.UID, binding.profileRecyclerView, requireContext(), infoAboutUser.userProfileImage, infoAboutUser.userName)
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRecyclerView.setHasFixedSize(true)

        // Настройка интерфейса
        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)
        fabControl.controlFabActionPosition(binding.profileRecyclerView, binding.fab)
        drawerControl(binding.drawerLayout, binding.content)

        binding.fab.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionAccountToCreatePostFragmentFragment())
        }

        binding.toolbar.drawerCall.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.RIGHT)
        }
    }

    private fun drawerControl(drawerLayout: DrawerLayout, contentLayout: ConstraintLayout) {
        val actionBarDrawerToggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.app_name, R.string.about) {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)

                    val slideX = drawerView.width * slideOffset
                    contentLayout.translationX = -slideX
                }
            }

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
    }
}
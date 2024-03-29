package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.create_post_toolbar.view.*
import kotlinx.android.synthetic.main.drawer_profile_content.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.databinding.FragmentUserProfileBinding
import ru.popkovden.messengerapplication.model.DrawerItemsModel
import ru.popkovden.messengerapplication.ui.adapters.profile.drawer.DrawerNavigationRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.profile.mainPart.PostsProfileRecyclerView
import ru.popkovden.messengerapplication.utils.customView.FabControl
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.changeStartDestination
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class UserProfileFragment : Fragment(){

    private lateinit var binding: FragmentUserProfileBinding
    private val fabControl: FabControl by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    lateinit var adapter: PostsProfileRecyclerView
    private val getPostsHelper: GetPosts by inject()
    private val uiHelper: StatusBarColorChanger by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение информации о профиле пользователя
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())

        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_test) as NavHostFragment?
        changeStartDestination(navHost, R.id.account)

        // Настройка адаптера постов
        getPostsHelper.getPosts(
            binding.profileRecyclerView,
            infoAboutUser.UID,
            requireContext(),
            infoAboutUser.userProfileImage,
            infoAboutUser.userName,
            "posts"
        )
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRecyclerView.setHasFixedSize(true)

        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.mainColor
        )

        binding.swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(IO).launch {
                getPostsHelper.getPosts(
                    binding.profileRecyclerView,
                    infoAboutUser.UID,
                    requireContext(),
                    infoAboutUser.userProfileImage,
                    infoAboutUser.userName,
                    "posts"
                )
                delay(2000)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        // Настройка адаптера drawer layout
        binding.drawerContent.contentDrawer.layoutManager = LinearLayoutManager(requireContext())
        val drawerList = arrayListOf<DrawerItemsModel>()
        drawerList.add(DrawerItemsModel(R.drawable.edit_icon, resources.getString(R.string.edit), resources.getDrawable(R.drawable.red_circle)))
        drawerList.add(DrawerItemsModel(R.drawable.moon_icon, resources.getString(R.string.dark), resources.getDrawable(R.drawable.dark_blue_circle)))
        drawerList.add(DrawerItemsModel(R.drawable.black_list_icon, resources.getString(R.string.black_list), resources.getDrawable(R.drawable.black_circle)))
        drawerList.add(DrawerItemsModel(R.drawable.security_icon, resources.getString(R.string.security), resources.getDrawable(R.drawable.orange_circle)))
        drawerList.add(DrawerItemsModel(R.drawable.about_icon, resources.getString(R.string.about), resources.getDrawable(R.drawable.green_circle)))
        binding.drawerContent.contentDrawer.adapter = DrawerNavigationRecyclerView(drawerList, infoAboutUser.userName, infoAboutUser.userProfileImage)
        binding.drawerContent.contentDrawer.setHasFixedSize(true)

        // Настройка интерфейса
        fabControl.controlFabActionPosition(binding.profileRecyclerView, binding.fab)

        drawerControl(binding.drawerLayout, binding.content)

        binding.fab.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionAccountToCreatePostFragmentFragment())
        }

        binding.toolbar.drawerCall.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.drawerContent.contactName.text = infoAboutUser.userName
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
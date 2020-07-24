package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.databinding.FragmentFeedScreenBinding
import ru.popkovden.messengerapplication.ui.adapters.profile.mainPart.PostsProfileRecyclerView
import ru.popkovden.messengerapplication.utils.helper.getData.setPhotoCount
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class FeedScreenFragment : Fragment() {

    private lateinit var binding: FragmentFeedScreenBinding
    private val getPostsHelper: GetPosts by inject()
    private val infoAboutUser: InfoAboutUser by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_screen, container, false)

        InfoAboutUser.loadInfoFromSharedPreferences(requireContext())

        if (InfoAboutUser.setPhotoCount) {
            setPhotoCount(InfoAboutUser.UID)
            InfoAboutUser.setPhotoCount = false
        }

        val adapter = PostsProfileRecyclerView(requireContext(), arrayListOf(), InfoAboutUser.UID)

        // Настройка адаптера постов
        getPostsHelper.getPosts(
            binding.feedRecyclerView,
            infoAboutUser.UID,
            requireContext(),
            infoAboutUser.userProfileImage,
            infoAboutUser.userName,
            "postsFromFriends"
        )
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.feedRecyclerView.setHasFixedSize(true)

        binding.swipeRefreshLayoutFeed.setColorSchemeResources(
            R.color.mainColor
        )

        binding.swipeRefreshLayoutFeed.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                getPostsHelper.getPosts(
                    binding.feedRecyclerView,
                    infoAboutUser.UID,
                    requireContext(),
                    infoAboutUser.userProfileImage,
                    infoAboutUser.userName,
                    "postsFromFriends"
                )
                delay(2000)
                withContext(Main) {
                    binding.swipeRefreshLayoutFeed.isRefreshing = false
                }
            }
        }

        if (adapter.itemCount <= 0) {
            binding.feedWithoutPosts.visibility = View.VISIBLE
            binding.pleaseCreateFirst.visibility = View.VISIBLE
        } else {
            binding.feedWithoutPosts.visibility = View.GONE
            binding.pleaseCreateFirst.visibility = View.GONE
        }

        return binding.root
    }
}
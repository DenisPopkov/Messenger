package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.create_post_panel.view.*
import kotlinx.android.synthetic.main.create_post_toolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.posts.CreatePost
import ru.popkovden.messengerapplication.databinding.FragmentCreatePostFragmentBinding
import ru.popkovden.messengerapplication.model.PostsModel
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.FileSliderRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.ImageSliderRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.VideoSliderRecyclerView
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class CreatePostFragmentFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostFragmentBinding
    private val createPostHelper: CreatePost by inject()
    private val infoUser: InfoAboutUser by inject()
    private val imageSlider = arrayListOf<String>()
    private val videoSlider = arrayListOf<String>()
    private val documentSlider = arrayListOf<String>()
    private var mergeAdapter = MergeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_post_fragment, container, false)

        // Настройка адаптера
        mergeAdapter = MergeAdapter()
        binding.imagesListViewPager.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.imagesListViewPager.adapter = mergeAdapter

        binding.createPostToolbar.backToProfile.setOnClickListener {
            backToProfile()
        }

        binding.panel.createPost.setOnClickListener {
            if (binding.postMainText.text.toString().isBlank()) {
                Toast.makeText(requireContext(), "Пустая запись", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(IO).launch {
                    createPostHelper.createPost(PostsModel(imageSlider, videoSlider, documentSlider,
                        "12", "Разработка ${System.currentTimeMillis()}", binding.postMainText.text.toString()), infoUser.UID)
                }

                backToProfile()
            }
        }

        binding.panel.imagePick.setOnClickListener {
            openFileFinder("image/*")
        }

        binding.panel.videoPick.setOnClickListener {
            openFileFinder("video/*")
        }

        binding.panel.documentPick.setOnClickListener {
            openFileFinder("*/*")
        }

        return binding.root
    }

    private fun backToProfile() {
        val action = CreatePostFragmentFragmentDirections.actionCreatePostToAccount()
        findNavController().navigate(action)
    }

    private fun openFileFinder(type: String) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = type
        startActivityForResult(intent, 1)
    }

    private fun getDataFromExternal(resultCode: Int, data: Intent?, list: ArrayList<String>) {
        if (resultCode == RESULT_OK) {
            if (data?.clipData != null) {  // Для нескольких элементов
                val count = data.clipData!!.itemCount

                for (i in 0 until count) {
                    val uri = data.clipData!!.getItemAt(i).uri.toString()
                    list.add(uri)
                }

            } else if (data?.data != null) { // Для одного элемента

                val uri = data.data.toString()
                list.add(uri)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imageSlider.clear()
        videoSlider.clear()
        documentSlider.clear()

        when (requestCode) {

            1 -> {
                getDataFromExternal(resultCode, data, imageSlider)
                mergeAdapter.addAdapter(ImageSliderRecyclerView(imageSlider, requireContext()))
            }

            2 -> {
                getDataFromExternal(resultCode, data, videoSlider)
                mergeAdapter.addAdapter(VideoSliderRecyclerView(videoSlider, requireContext()))
            }

            3 -> {
                getDataFromExternal(resultCode, data, documentSlider)
                mergeAdapter.addAdapter(FileSliderRecyclerView(documentSlider, requireContext()))
            }
        }
    }
}
package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
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
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
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
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.ImageSliderRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.VideoSliderRecyclerView
import ru.popkovden.messengerapplication.utils.helper.READ_EXTERNAL_STORAGE
import ru.popkovden.messengerapplication.utils.helper.checkPermission
import ru.popkovden.messengerapplication.utils.helper.getPath
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import java.io.File

class CreatePostFragmentFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostFragmentBinding
    private val createPostHelper: CreatePost by inject()
    private val infoUser: InfoAboutUser by inject()
    private val imageSlider = arrayListOf<String>()
    private val videoSlider = arrayListOf<String>()
    private val compressImages = arrayListOf<String>()
    private var mergeAdapter = MergeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_post_fragment, container, false)

        compressImages.clear()
        imageSlider.clear()
        videoSlider.clear()

        // Настройка адаптера
        mergeAdapter = MergeAdapter()
        binding.imagesListViewPager.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.imagesListViewPager.adapter = mergeAdapter

        binding.createPostToolbar.backToProfile.setOnClickListener {
            backToProfile()
        }

        binding.panel.createPost.setOnClickListener {

            val textFromPost = binding.postMainText.text.toString()
            var header = textFromPost

            if (textFromPost.isBlank()) {
                Toast.makeText(requireContext(), "Пустая запись", Toast.LENGTH_SHORT).show()
            } else {

                header = if (textFromPost.length <= 25)  {
                    textFromPost
                } else {
                    header.take(24) + "..."
                }
                
                if (compressImages.size == imageSlider.size) {
                    createPostHelper.createPost(PostsModel(compressImages, videoSlider, "0", header, textFromPost, ""), infoUser.UID)
                    backToProfile()
                } else {
                    Toast.makeText(requireContext(), resources.getString(R.string.photo_loading), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.panel.imagePick.setOnClickListener {
            if (checkPermission(READ_EXTERNAL_STORAGE, requireActivity())) {
                openFileFinder("image/*")
            }
        }

        binding.panel.videoPick.setOnClickListener {
            if (checkPermission(READ_EXTERNAL_STORAGE, requireActivity())) {
                openFileFinder("video/*")
            }
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

                CoroutineScope(IO).launch {
                    for (i in 0 until count) {

                        val uri = data.clipData!!.getItemAt(i).uri.toString()

                        val compressedImageFile = Compressor.compress(requireContext(), File(getPath(requireContext(), Uri.parse(uri))!!)) {
                            resolution(1280, 720)
                            size(1_097_152) // 1 MB
                        }

                        compressImages.add(Uri.fromFile(compressedImageFile).toString())
                    }
                }

            } else if (data?.data != null) { // Для одного элемента

                val uri = data.data.toString()

                list.add(uri)

                CoroutineScope(IO).launch {
                    val compressedImageFile = Compressor.compress(requireContext(), File(getPath(requireContext(), Uri.parse(uri))!!)) {
                        resolution(1280, 720)
                        size(1_097_152) // 1 MB
                    }
                    compressImages.add(Uri.fromFile(compressedImageFile).toString())
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imageSlider.clear()
        videoSlider.clear()

        when (requestCode) {

            1 -> {
                getDataFromExternal(resultCode, data, imageSlider)
                mergeAdapter.addAdapter(ImageSliderRecyclerView(imageSlider, requireContext()))
            }

            2 -> {
                getDataFromExternal(resultCode, data, videoSlider)
                mergeAdapter.addAdapter(VideoSliderRecyclerView(videoSlider, requireContext()))
            }
        }
    }
}
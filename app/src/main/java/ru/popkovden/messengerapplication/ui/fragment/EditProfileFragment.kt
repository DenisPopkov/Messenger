package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.auth.CreateUser
import ru.popkovden.messengerapplication.databinding.FragmentEditProfileBinding
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.DecodeUri
import ru.popkovden.messengerapplication.viewmodel.EditProfileFragmentViewModel

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    var name = ""
    private var userImageUri: Uri? = null
    private val userCreateHelper: CreateUser by inject()
    private val viewModel: EditProfileFragmentViewModel by viewModel()
    private val decodeUri: DecodeUri by inject()
    private val uiHelper: StatusBarColorChanger by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        uiHelper.changeStatusBarColor(requireActivity(), R.color.mainColor)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        arguments?.let {
            userImageUri = Uri.parse(EditProfileFragmentArgs.fromBundle(it).userImage)
            name = EditProfileFragmentArgs.fromBundle(it).userName
        }

        binding.userNameInput.setText(name)
        Glide.with(requireContext()).load(userImageUri).into(binding.profileImage)

        binding.profileImage.setOnClickListener {
            openGalleryForOnePicture()
        }

        binding.toMainScreen.setOnClickListener {
            name = binding.userNameInput.text.toString()

            if (name.isBlank()) {
                Toast.makeText(requireContext(), resources.getText(R.string.please_fill_all_data), Toast.LENGTH_SHORT).show()
            } else {
                userCreateHelper.updateUserInfo(name, requireContext())
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToAccount())
            }
        }

        viewModel.currentUriLiveData.observe(viewLifecycleOwner, Observer { uri ->
            val image = decodeUri.decodeUriToBitmap(uri!!, requireContext())
            binding.profileImage.setImageBitmap(image)
        })

        return binding.root
    }

    private fun openGalleryForOnePicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, 1)
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(3000, 3000)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(requireContext(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) launchImageCrop(data?.data!!)

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = decodeUri.decodeUriToBitmap(result.uri, requireContext())
                    viewModel.updateUri(result.uri)
                    binding.profileImage.setImageBitmap(bitmap)
                    userImageUri = result.uri
                }

                userCreateHelper.loadImageToDatabase(userImageUri!!)
            }
        }
    }
}
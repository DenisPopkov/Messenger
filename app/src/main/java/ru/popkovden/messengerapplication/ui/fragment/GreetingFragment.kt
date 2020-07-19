package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.auth.CreateUser
import ru.popkovden.messengerapplication.databinding.FragmentGreetingBinding
import ru.popkovden.messengerapplication.utils.customView.SnackBarView
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.DecodeUri
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.utils.internetChecker.CheckInternetConnection
import ru.popkovden.messengerapplication.viewmodel.GreetingFragmentViewModel

class GreetingFragment : Fragment() {

    private lateinit var binding: FragmentGreetingBinding
    private var number = ""
    private var uid = ""
    private var userName = ""
    private var userImageUri: Uri? = null
    private val userCreateHelper: CreateUser by inject()
    private val viewModel: GreetingFragmentViewModel by viewModel()
    private val decodeUri: DecodeUri by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private val uiHelper: StatusBarColorChanger by inject()
    private val customView: SnackBarView by inject()
    private val checkInternetConnection: CheckInternetConnection by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_greeting, container, false)

        infoAboutUser.UID = FirebaseAuth.getInstance().uid.toString() // Получение UID пользователя
        uid = infoAboutUser.UID

        arguments?.let {
            number = GreetingFragmentArgs.fromBundle(it).phoneNumber
        }

        binding.userImage.setOnClickListener {
            openGalleryForOnePicture()
        }

        binding.toMainScreen.setOnClickListener {

            userName = binding.userNameInput.text.toString()
            infoAboutUser.userName = userName

            if (userName.isBlank()) {
                Toast.makeText(requireContext(), resources.getText(R.string.please_fill_all_data), Toast.LENGTH_SHORT).show()
            } else {
                val userInfo = hashMapOf<String, String>()
                userInfo["userName"] = infoAboutUser.userName
                userInfo["phoneNumber"] = infoAboutUser.phoneNumber
                userInfo["UID"] = infoAboutUser.UID
                userInfo["userProfileImage"] = infoAboutUser.userProfileImage
                userInfo["token"] = ""
                Log.d("efefe", "${infoAboutUser.userName}, ${infoAboutUser.phoneNumber}, ${infoAboutUser.userProfileImage},  ${infoAboutUser.UID} - uid last okay")
                userCreateHelper.userCreate(requireContext(), userInfo, InfoAboutUser, infoAboutUser.UID)
                findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToMainChatScreenFragment())
            }
        }

        viewModel.currentUriLiveData.observe(viewLifecycleOwner, Observer { uri ->
            val image = decodeUri.decodeUriToBitmap(uri!!, requireContext())
            binding.userImage.setImageBitmap(image)
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
            1 -> if (resultCode == RESULT_OK) launchImageCrop(data?.data!!)

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)

                if (resultCode == RESULT_OK) {
                    val bitmap = decodeUri.decodeUriToBitmap(result.uri, requireContext())
                    viewModel.updateUri(result.uri)
                    binding.userImage.setImageBitmap(bitmap)
                    userImageUri = result.uri
                }

                InfoAboutUser.userProfileImageFile = userImageUri.toString()
                userCreateHelper.loadImageToDatabase(userImageUri!!, infoAboutUser.UID, binding.toMainScreen)
            }
        }
    }
}
package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.CreateUser
import ru.popkovden.messengerapplication.databinding.FragmentGreetingBinding

class GreetingFragment : Fragment() {

    private lateinit var binding: FragmentGreetingBinding
    private var number = ""
    private var uid = ""
    private var userName = ""
    private var userImageUri: Uri? = null
    private val userCreateHelper: CreateUser by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_greeting, container, false)

        arguments?.let {
            number = GreetingFragmentArgs.fromBundle(it).phoneNumber
        }

        binding.userImage.setOnClickListener {
            openGalleryForOnePicture()
        }

        binding.toMainScreen.setOnClickListener {

            userName = binding.userNameInput.text.toString()

            if (userName.isBlank()) {
                Toast.makeText(requireContext(), "Пожалуйста введите Ваше имя", Toast.LENGTH_SHORT).show()
            } else {
                uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                userCreateHelper.userCreate(userImageUri!!, number, userName, uid)
                findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToMainChatScreenFragment())
            }
        }

        return binding.root
    }

    private fun openGalleryForOnePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            userImageUri = data?.data

            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, userImageUri!!))
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, userImageUri)
            }

            binding.userImage.setImageBitmap(bitmap)
        }
    }
}
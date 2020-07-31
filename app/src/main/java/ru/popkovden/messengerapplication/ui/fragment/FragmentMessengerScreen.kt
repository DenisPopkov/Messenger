package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.iid.FirebaseInstanceId
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.send_message.view.*
import kotlinx.android.synthetic.main.toolbar_for_messaging.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.images.SendImages
import ru.popkovden.messengerapplication.data.repository.messages.GetMessages
import ru.popkovden.messengerapplication.data.repository.messages.SendMessageToUser
import ru.popkovden.messengerapplication.data.repository.notification.FirebaseNotificationService
import ru.popkovden.messengerapplication.data.repository.notification.RetrofitInstance
import ru.popkovden.messengerapplication.databinding.FragmentMessengerScreenBinding
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.model.SentImageModel
import ru.popkovden.messengerapplication.model.notification.NotificationData
import ru.popkovden.messengerapplication.model.notification.PushNotificationModel
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.ImageSliderRecyclerView
import ru.popkovden.messengerapplication.utils.helper.*
import ru.popkovden.messengerapplication.utils.helper.getData.*
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.MessengerFragmentViewModel
import java.io.File

class FragmentMessengerScreen : Fragment() {

    private lateinit var binding: FragmentMessengerScreenBinding
    private val sendMessageHelper: SendMessageToUser by inject()
    private val sendImagesHelper: SendImages by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private val getSentMessagesHelper: GetMessages by inject()
    private val viewModel: MessengerFragmentViewModel by inject()
    private var UID = ""
    private var userPhoto = ""
    private var onlineStatus = ""
    private var screen = ""

    val job = IO + Job()

    companion object {
        var userUID = ""
        var collectionSize = 0
    }

    private val compressImages = arrayListOf<String>()
    private val imageSlider = arrayListOf<String>()

    private var token = ""
    private var tokenFromContact = ""

    override fun onStop() {
        super.onStop()
        setOnlineStatus("offline")
        job.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger_screen, container, false)
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        // Подготавливает важные данные
        UID = infoAboutUser.UID

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получает необходимые данные для диалога
        arguments?.let {
            with(FragmentMessengerScreenArgs.fromBundle(it)) {
                userUID = this.UserUID.toString()
                userPhoto = this.UserPhoto.toString()
                Glide.with(requireContext()).load(this.UserPhoto.toString()).into(binding.messengerToolbar.userImage)
                binding.messengerToolbar.textView.text = this.UserName.toString()
            }
        }

        // Получает токен для отправки сообщений
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {

            FirebaseNotificationService.sharedPreferences = requireContext().getSharedPreferences("token", Context.MODE_PRIVATE)
            FirebaseNotificationService.token = it.token
            token = it.token
            setToken(UID, token)
        }

        CoroutineScope(Main).launch {
            tokenFromContact = getToken(userUID)
            onlineStatus = getOnlineStatus(userUID)
            binding.messengerToolbar.onlineStatus.text = onlineStatus
        }

        CoroutineScope(job).launch {
            while (true) {
                delay(1000)
                updateScreenDestination("MessengerScreen", userUID)
                screen = getScreenDestination(userUID)

                if (screen.contains(UID)) {
                    delay(1000)
                    readAllMessagesInChat(UID, userUID, "sentMessages")
                    delay(1000)
                    readAllMessagesInChat(UID, userUID, "receivedMessages")
                    delay(1000)
                    readAllMessagesInChat(UID, userUID, "sentImages")
                    delay(1000)
                    readAllMessagesInChat(UID, userUID, "receivedImages")
                }
            }
        }

        // Настривает адапер
        getSentMessagesHelper.getMessages(infoAboutUser.UID, userUID, binding.messengerScreenRecyclerView, requireContext(), binding.messageEmpty, binding.messageStart)
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.messengerScreenRecyclerView.layoutManager = linearLayoutManager

        // Настраивает адаптер для фото
        viewModel.currentImagesLiveData.observe(viewLifecycleOwner, Observer { images ->

            val adapter = ImageSliderRecyclerView(images, requireContext(), binding.imagesRecyclerView, FragmentMessengerScreenDirections
                .actionMessengerToZoomImagesFragment(imageSlider.toTypedArray(), 0), binding.linearLayoutCompat2.microphoneIcon)

            if (adapter.imagesSliderList.size > 0) {
                binding.imagesRecyclerView.visibility = View.VISIBLE
                binding.bottomMessage.microphoneIcon.setImageResource(R.drawable.send_icon)
            } else {
                binding.imagesRecyclerView.visibility = View.GONE
                binding.bottomMessage.microphoneIcon.setImageResource(R.drawable.microphone_icon)
            }

            binding.imagesRecyclerView.adapter = adapter
        })

        binding.messengerToolbar.backToContactList.setOnClickListener {
            val action = FragmentMessengerScreenDirections.actionFragmentMessengerScreenToChat()
            findNavController().navigate(action)
        }

        // Получает количество сообщения в диалоге
        CoroutineScope(IO).launch {
            while (true) {
                try {
                    collectionSize = getCollectionSize(infoAboutUser.UID, userUID)!!
                } catch (e: Exception) {
                }
            }
        }

        // Показывает BottomSheetDialog
        binding.bottomMessage.attachFile.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.chat_bottom_sheet, view.findViewById(R.id.container))

            bottomSheet.setContentView(bottomSheetView)
            bottomSheet.show()

            val camera: AppCompatTextView = bottomSheetView.findViewById(R.id.camera_text)
            camera.setOnClickListener {
                openFileFinder("image/*")
                bottomSheet.dismiss()
            }
        }

        // Отправляет сообщение
        binding.bottomMessage.microphoneIcon.setOnClickListener {

            viewModel.currentImagesLiveData.observe(viewLifecycleOwner, Observer {
                if (it.size > 0) {
                    if (viewModel.currentImagesLiveData.value!!.size == imageSlider.size) {
                        sendImagesHelper.sendImages(UID, userUID, SentImageModel(
                                imageSlider, getCurrentDateTime().toString("HH:mm"), "$UID-$userUID", ""))
                        binding.imagesRecyclerView.visibility = View.GONE
                        binding.bottomMessage.microphoneIcon.setImageResource(R.drawable.microphone_icon)
                        updateCollectionSize(UID, collectionSize, userUID)

                        PushNotificationModel(NotificationData(InfoAboutUser.userName, "Фото", InfoAboutUser.userProfileImage, userUID, screen), tokenFromContact).also {notification ->
                            sendNotification(notification)
                        }
                    } else {
                        Toast.makeText(requireContext(), resources.getString(R.string.photo_loading), Toast.LENGTH_SHORT).show()
                    }
                }
            })

            val textInput = binding.bottomMessage.messageInput.text.toString()

            if (textInput.isNotEmpty()) {

                val currentTime = getCurrentDateTime().toString("HH:mm")

                PushNotificationModel(NotificationData(InfoAboutUser.userName, textInput, InfoAboutUser.userProfileImage, userUID, screen), tokenFromContact).also {
                    sendNotification(it)
                }

                sendMessageHelper.sendMessage(infoAboutUser.UID, userUID, SendMessageModel(textInput, currentTime, UID, collectionSize, "false", "", 0))
                updateCollectionSize(UID, collectionSize, userUID)
                binding.bottomMessage.messageInput.text?.clear()
            }
        }
    }

    private fun openFileFinder(type: String) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = type
        startActivityForResult(intent, 1)
    }

    private fun sendNotification(notification: PushNotificationModel) = CoroutineScope(IO).launch {

       RetrofitInstance.api.postNotification(notification)
    }

    private fun getDataFromExternal(resultCode: Int, data: Intent?, list: ArrayList<String>) {
        if (resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {  // Для нескольких элементов
                val count = data.clipData!!.itemCount

                for (i in 0 until count) {
                    val uri = data.clipData!!.getItemAt(i).uri.toString()
                    list.add(uri)
                    viewModel.addAll(list)
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

                    viewModel.addAll(compressImages)
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
                    viewModel.addAll(compressImages)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            getDataFromExternal(resultCode, data, imageSlider)
        }
    }
}
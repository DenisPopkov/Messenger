package ru.popkovden.messengerapplication.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.toolbar_for_messaging.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.images.SendImages
import ru.popkovden.messengerapplication.data.repository.messages.GetMessages
import ru.popkovden.messengerapplication.data.repository.messages.SendMessageToUser
import ru.popkovden.messengerapplication.databinding.FragmentMessengerScreenBinding
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.model.SentImageModel
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.getData.getCollectionSize
import ru.popkovden.messengerapplication.utils.helper.getData.getCurrentDateTime
import ru.popkovden.messengerapplication.utils.helper.getData.toString
import ru.popkovden.messengerapplication.utils.helper.getData.updateCollectionSize
import ru.popkovden.messengerapplication.utils.helper.getPath
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import java.io.File

class FragmentMessengerScreen : Fragment() {

    private lateinit var binding: FragmentMessengerScreenBinding
    private val uiHelper: StatusBarColorChanger by inject()
    private val sendMessageHelper: SendMessageToUser by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private val getSentMessagesHelper: GetMessages by inject()
    private var userUID = ""
    private var UID = ""
    private var collectionSize = 0

    private val compressImages = arrayListOf<String>()
    private val imageSlider = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger_screen, container, false)
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        // Подготавливает важные данные
        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)
        UID = infoAboutUser.UID

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получает необходимые данные для диалога
        arguments?.let {
            with(FragmentMessengerScreenArgs.fromBundle(it)) {
                userUID = this.UserUID.toString()
                Glide.with(requireContext()).load(this.UserPhoto.toString()).into(binding.messengerToolbar.userImage)
                binding.messengerToolbar.textView.text = this.UserName.toString()
            }
        }

        // Настривает адапер
        getSentMessagesHelper.getMessages(infoAboutUser.UID, userUID, binding.messengerScreenRecyclerView, requireContext(), binding.messageEmpty, binding.messageStart)
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.messengerScreenRecyclerView.layoutManager = linearLayoutManager

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

        binding.bottomMessage.appCompatImageButton3.setOnClickListener {
            if (compressImages.size == imageSlider.size) {
                updateCollectionSize(UID, collectionSize, userUID)
                SendImages.sendImages(UID, userUID, SentImageModel(imageSlider, getCurrentDateTime()
                    .toString("HH:mm"), "$UID-$userUID"))
            }
        }

        // Отправляет сообщение
        binding.bottomMessage.microphoneIcon.setOnClickListener {

            val textInput = binding.bottomMessage.messageInput.text.toString()

            if (textInput.isNotEmpty()) {

                val currentTime = getCurrentDateTime().toString("HH:mm")

                sendMessageHelper.sendMessage(infoAboutUser.UID, userUID, SendMessageModel(textInput, currentTime, UID, collectionSize, 0))
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

    private fun getDataFromExternal(resultCode: Int, data: Intent?, list: ArrayList<String>) {
        if (resultCode == Activity.RESULT_OK) {
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

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            getDataFromExternal(resultCode, data, imageSlider)
        }
    }
}
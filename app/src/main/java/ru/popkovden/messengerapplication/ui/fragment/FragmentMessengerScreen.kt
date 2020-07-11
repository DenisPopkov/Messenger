package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.chat_bottom_sheet.*
import kotlinx.android.synthetic.main.toolbar_for_messaging.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.messages.GetMessages
import ru.popkovden.messengerapplication.data.repository.messages.SendMessageToUser
import ru.popkovden.messengerapplication.databinding.FragmentMessengerScreenBinding
import ru.popkovden.messengerapplication.model.ChatBottomSheetModel
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.ui.adapters.chat.BottomSheetRecyclerView
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.getCollectionSize
import ru.popkovden.messengerapplication.utils.helper.getCurrentDateTime
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.utils.helper.toString
import ru.popkovden.messengerapplication.utils.helper.updateCollectionSize
import ru.popkovden.messengerapplication.viewmodel.MessengerFragmentViewModel

class FragmentMessengerScreen : Fragment() {

    private lateinit var binding: FragmentMessengerScreenBinding
    private val uiHelper: StatusBarColorChanger by inject()
    private val sendMessageHelper: SendMessageToUser by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private val getSentMessagesHelper: GetMessages by inject()
    private val viewModel: MessengerFragmentViewModel by viewModel()
    private var userUID = ""
    private var UID = ""
    private var collectionSize = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger_screen, container, false)
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())

        // Подготавливает важные данные
        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)
        UID = infoAboutUser.UID

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получает количество сообщения в диалоге
        CoroutineScope(IO).launch {
            collectionSize = getCollectionSize(infoAboutUser.UID)!!
        }

        // Получает необходимые данные для диалога
        arguments?.let {
            with(FragmentMessengerScreenArgs.fromBundle(it)) {
                userUID = this.UserUID.toString()
                Glide.with(requireContext()).load(this.UserPhoto.toString()).into(binding.messengerToolbar.userImage)
                binding.messengerToolbar.textView.text = this.UserName.toString()
            }
        }

        // Настривает адапер
        getSentMessagesHelper.getMessages(infoAboutUser.UID, userUID, binding.messengerScreenRecyclerView)
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.messengerScreenRecyclerView.layoutManager = linearLayoutManager

        binding.messengerToolbar.backToContactList.setOnClickListener {
            val action = FragmentMessengerScreenDirections.actionFragmentMessengerScreenToChat()
            findNavController().navigate(action)
        }

        // Показывает BottomSheetDialog
        binding.bottomMessage.attachFile.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.chat_bottom_sheet, view.findViewById(R.id.container))

            bottomSheet.setContentView(bottomSheetView)
            bottomSheet.sheet_recyclerView.layoutManager = LinearLayoutManager(requireContext())
            val bottomSheetList = arrayListOf<ChatBottomSheetModel>()
            bottomSheetList.add(ChatBottomSheetModel(resources.getDrawable(R.drawable.camera_icon), "Камера", resources.getDrawable(R.drawable.camera_elips)))
            bottomSheetList.add(ChatBottomSheetModel(resources.getDrawable(R.drawable.gallery_sheet_icon), "Фото или видео", resources.getDrawable(R.drawable.purple_elips)))
            bottomSheetList.add(ChatBottomSheetModel(resources.getDrawable(R.drawable.file_icon), "Файл без сжатия", resources.getDrawable(R.drawable.file_elips)))
            bottomSheet.sheet_recyclerView.adapter = BottomSheetRecyclerView(bottomSheetList)
            bottomSheet.show()
        }

        // Отправляет сообщение
        binding.bottomMessage.microphoneIcon.setOnClickListener {

            val textInput = binding.bottomMessage.messageInput.text.toString()

            if (textInput.isNotEmpty()) {

                val currentTime = getCurrentDateTime().toString("HH:mm")

                sendMessageHelper.sendMessage(infoAboutUser.UID, userUID, SendMessageModel(textInput, currentTime, UID, collectionSize, 0))
                updateCollectionSize(UID, collectionSize)
                binding.bottomMessage.messageInput.text?.clear()
            }
        }
    }
}
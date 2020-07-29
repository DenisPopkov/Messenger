package ru.popkovden.messengerapplication.data.repository.messages

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.MessageModel
import ru.popkovden.messengerapplication.ui.DiffUtilMessengerHelper
import ru.popkovden.messengerapplication.ui.adapters.chat.messeges.MessagesRecyclerViewAdapter

object GetMessages {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var messagesRequestList = arrayListOf<MessageModel>()

    fun getMessages(UID: String, UserUID: String, recyclerViewAdapter: RecyclerView, context: Context, emojiText: AppCompatTextView, startText: AppCompatTextView) {

        messagesRequestList.clear()

        CoroutineScope(IO).launch {

            // Получает с БД "отправленные" от другого пользователя сообщения
            firebaseFirestore.collection("users").document(UID)
                .collection("chats").document(UserUID).collection("sentMessages").addSnapshotListener { documentSnapshot, _ ->

                    // Получает данные
                    val messagesRequest = documentSnapshot?.documents
                    messagesRequestList.clear()

                    // Перебирает данные
                    for (message in messagesRequest!!) {
                        val sentMessagesHashMap = hashMapOf<String, Any>()
                        val sentMessages = arrayListOf<HashMap<String, Any>>()
                        sentMessagesHashMap["message"] = message["message"].toString()
                        sentMessagesHashMap["time"] = message["time"].toString()
                        sentMessagesHashMap["wasRead"] = message["wasRead"].toString()
                        val messageId = message["id"].toString().toInt()
                        sentMessages.add(sentMessagesHashMap)
                        messagesRequestList.add(MessageModel(sentMessages, null, null, null,1, messageId))
                    }

                    getSentImages(UID, UserUID)
                    getReceivedImages(UID, UserUID)
                    // Получает с БД "полученные" от другого пользователя сообщения
                    getReceivedMessages(recyclerViewAdapter, UID, UserUID, context, emojiText, startText)
                    // Получает с БД "отправленные" фото
//                    getSentImages(recyclerViewAdapter, UID, UserUID, context)
                }
        }
    }

    private fun getReceivedMessages(recyclerViewAdapter: RecyclerView, UID: String, UserUID: String, context: Context, emojiText: AppCompatTextView, startText: AppCompatTextView) {

        // Получает с БД "полученные" от другого пользователя сообщения
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("receivedMessages")
            .addSnapshotListener { documentSnapshot, _ ->

                // Получает данные
                val receivedMessagesRequest = documentSnapshot?.documents

                // Перебирает данные
                for (messages in receivedMessagesRequest!!) {
                    val receivedMessagesHashMap = hashMapOf<String, Any>()
                    val receivedMessages = arrayListOf<HashMap<String, Any>>()
                    receivedMessagesHashMap["message"] = messages["message"].toString()
                    receivedMessagesHashMap["time"] = messages["time"].toString()
                    receivedMessagesHashMap["wasRead"] = messages["wasRead"].toString()
                    val messageId = messages["id"].toString().toInt()
                    receivedMessages.add(receivedMessagesHashMap)
                    messagesRequestList.add(MessageModel(null, receivedMessages, null, null, 2, messageId))
                }

                // Передает данные в адаптер, отфильтрованные по ID
                val adapter = MessagesRecyclerViewAdapter(messagesRequestList.sortedWith(compareBy { it.id }).toMutableSet().toMutableList(), context)
                recyclerViewAdapter.adapter = adapter

                val authorDiffUtilCallback = DiffUtilMessengerHelper(adapter.sentMessageList, messagesRequestList.sortedWith(compareBy { it.id }).toMutableSet().toMutableList())
                val authorDiffResult = DiffUtil.calculateDiff(authorDiffUtilCallback)
                adapter.sentMessageList = messagesRequestList.sortedWith(compareBy { it.id }).toMutableSet().toMutableList().distinctBy { it.id }
                authorDiffResult.dispatchUpdatesTo(adapter)
                recyclerViewAdapter.smoothScrollToPosition(messagesRequestList.size)

                if (messagesRequestList.isNullOrEmpty()) {
                    emojiText.visibility = View.VISIBLE
                    startText.visibility = View.VISIBLE
                } else {
                    emojiText.visibility = View.GONE
                    startText.visibility = View.GONE
                }
            }
    }

    private fun getSentImages(UID: String, UserUID: String) {

        // Получает с БД "полученные" от другого пользователя сообщения
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("sentImages")
            .addSnapshotListener { documentSnapshot, _ ->

                // Получает данные
                val sentImagesRequest = documentSnapshot?.documents

                // Перебирает данные
                for (images in sentImagesRequest!!) {
                    val sentImagesHashMap = hashMapOf<String, Any>()
                    val sentImages = arrayListOf<HashMap<String, Any>>()
                    sentImagesHashMap["image"] = images["image"].toString()
                    sentImagesHashMap["time"] = images["time"].toString()
                    val messageId = images["id"].toString().toInt()
                    sentImages.add(sentImagesHashMap)
                    messagesRequestList.add(MessageModel(null, null, sentImages, null,3, messageId))
                }
            }
    }

    private fun getReceivedImages(UID: String, UserUID: String) {

        // Получает с БД "полученные" от другого пользователя сообщения
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("receivedImages")
            .addSnapshotListener { documentSnapshot, _ ->

                // Получает данные
                val receivedImagesRequest = documentSnapshot?.documents

                // Перебирает данные
                for (images in receivedImagesRequest!!) {
                    val receivedImagesHashMap = hashMapOf<String, Any>()
                    val receivedImages = arrayListOf<HashMap<String, Any>>()
                    receivedImagesHashMap["images"] = images["images"].toString()
                    receivedImagesHashMap["time"] = images["time"].toString()
                    val messageId = images["id"].toString().toInt()
                    receivedImages.add(receivedImagesHashMap)
                    messagesRequestList.add(MessageModel(null, null, null, receivedImages,3, messageId))
                }
            }
    }
}
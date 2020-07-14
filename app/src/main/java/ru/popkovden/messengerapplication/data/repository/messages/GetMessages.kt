package ru.popkovden.messengerapplication.data.repository.messages

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.MessageModel
import ru.popkovden.messengerapplication.ui.adapters.chat.messeges.MessagesRecyclerViewAdapter

object GetMessages {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var messagesRequestList = arrayListOf<MessageModel>()

    fun clear() {
        messagesRequestList.clear()
    }

    fun getMessages(UID: String, UserUID: String, recyclerViewAdapter: RecyclerView, context: Context) {

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
                        val messageId = message["id"].toString().toInt()
                        sentMessages.add(sentMessagesHashMap)
                        messagesRequestList.add(MessageModel(sentMessages, null, null, 1, messageId))
                    }

//                    recyclerViewAdapter.adapter = MessagesRecyclerViewAdapter(messagesRequestList.sortedWith(compareBy { it.id }), context)

                    // Получает с БД "полученные" от другого пользователя сообщения
                    getReceivedMessages(recyclerViewAdapter, UID, UserUID, context)
                    // Получает с БД "отправленные" фото
//                    getSentImages(recyclerViewAdapter, UID, UserUID, context)
                }
        }
    }

    private fun getReceivedMessages(recyclerViewAdapter: RecyclerView, UID: String, UserUID: String, context: Context) {

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
                    val messageId = messages["id"].toString().toInt()
                    receivedMessages.add(receivedMessagesHashMap)
                    messagesRequestList.add(MessageModel(null, receivedMessages, null, 2, messageId))
                }

                // Передает данные в адаптер, отфильтрованные по ID
                recyclerViewAdapter.adapter = MessagesRecyclerViewAdapter(messagesRequestList.sortedWith(compareBy { it.id }).toMutableSet().toMutableList(), context)
            }
    }

    private fun getSentImages(recyclerViewAdapter: RecyclerView, UID: String, UserUID: String, context: Context) {

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
                    sentImagesHashMap["images"] = images["images"].toString()
                    sentImagesHashMap["time"] = images["time"].toString()
                    val messageId = images["id"].toString().toInt()
                    sentImages.add(sentImagesHashMap)
                    messagesRequestList.add(MessageModel(null, null, sentImages, 3, messageId))
                }

                // Передает данные в адаптер, отфильтрованные по ID
                recyclerViewAdapter.adapter = MessagesRecyclerViewAdapter(messagesRequestList.sortedWith(compareBy { it.id }), context)
            }
    }
}
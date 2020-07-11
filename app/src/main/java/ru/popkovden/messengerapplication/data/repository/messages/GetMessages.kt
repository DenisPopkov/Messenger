package ru.popkovden.messengerapplication.data.repository.messages

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

    fun getMessages(UID: String, UserUID: String, recyclerViewAdapter: RecyclerView) {

        CoroutineScope(IO).launch {

            // Получает с БД "отправленные" от другого пользователя сообщения
            firebaseFirestore.collection("users").document(UID)
                .collection("chats").document(UserUID).collection("sentMessages")
                .addSnapshotListener { documentSnapshot, _ ->

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
                        messagesRequestList.add(MessageModel(sentMessages, null, 1, messageId))
                    }

                    // Получает с БД "полученные" от другого пользователя сообщения
                    getReceivedMessages(recyclerViewAdapter, UID, UserUID)
                }
        }
    }

    private fun getReceivedMessages(recyclerViewAdapter: RecyclerView, UID: String, UserUID: String) {

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
                    messagesRequestList.add(MessageModel(null, receivedMessages, 2, messageId))
                }

                // Передает данные в адаптер, отфильтрованные по ID
                recyclerViewAdapter.adapter = MessagesRecyclerViewAdapter(messagesRequestList.sortedWith(compareBy { it.id }))
            }
    }
}
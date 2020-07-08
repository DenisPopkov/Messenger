package ru.popkovden.messengerapplication.data.repository.messages

import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.ui.adapters.chat.messeges.ReceivedMessagesRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.chat.messeges.SentMessagesRecyclerView

object GetMessages {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var receivedMessagesRequestList = arrayListOf<HashMap<String, Any>>()
    private var sentMessagesRequestList = arrayListOf<HashMap<String, Any>>()

    fun getMessages(UID: String, UserUID: String, recyclerViewAdapter: RecyclerView) {

        CoroutineScope(IO).launch {

            // Получает с БД "отпрвленные" от другого пользователя сообщения
            firebaseFirestore.collection("users").document(UID)
                .collection("chats").document(UserUID).collection("sentMessages")
                .addSnapshotListener { documentSnapshot, _ ->

                    // Получает данные
                    val messagesRequest = documentSnapshot?.documents
                    sentMessagesRequestList.clear()

                    // Настраивает параметры адаптера
                    val mergeAdapter = MergeAdapter()
                    recyclerViewAdapter.adapter = mergeAdapter

                    // Перебирает данные
                    for (message in messagesRequest!!) {
                        val sentMessagesHashMap = hashMapOf<String, Any>()
                        sentMessagesHashMap["message"] = message["message"].toString()
                        sentMessagesHashMap["time"] = message["time"].toString()
                        sentMessagesRequestList.add(sentMessagesHashMap)
                    }

                    // Устанавливает адаптер, на отправленные сообщения
                    val sentMessagesRecyclerView = SentMessagesRecyclerView(sentMessagesRequestList)
                    mergeAdapter.addAdapter(sentMessagesRecyclerView)

                    getReceivedMessages(mergeAdapter, UID, UserUID)
                }
        }
    }

    private fun getReceivedMessages(mergeAdapter: MergeAdapter, UID: String, UserUID: String) {

        // Получает с БД "полученные" от другого пользователя сообщения
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("receivedMessages")
            .addSnapshotListener { documentSnapshot2, _ ->

                // Получает данные
                val receivedMessagesRequest = documentSnapshot2?.documents
                receivedMessagesRequestList.clear()

                // Перебирает данные
                for (message in receivedMessagesRequest!!) {
                    val receivedMessagesHashMap = hashMapOf<String, Any>()
                    receivedMessagesHashMap["message"] = message["message"].toString()
                    receivedMessagesHashMap["time"] = message["time"].toString()
                    receivedMessagesRequestList.add(receivedMessagesHashMap)
                }

                // Устанавливает адаптер, на полученные сообщения
                mergeAdapter.addAdapter(ReceivedMessagesRecyclerView(receivedMessagesRequestList))
            }
    }
}
package ru.popkovden.messengerapplication.data.repository.messages

import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.ui.adapters.chat.messeges.SentMessagesRecyclerView

object GetMessages {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var sentMessagesRequestList = arrayListOf<HashMap<String, Any>>()
    private var receivedMessagesRequestList = arrayListOf<HashMap<String, Any>>()

    fun getMessages(UID: String, UserUID: String, recyclerViewAdapter: RecyclerView){

        CoroutineScope(IO).launch {

            firebaseFirestore.collection("users").document(UID)
                .collection("chats").document(UserUID).collection("sentMessages")
                .addSnapshotListener { documentSnapshot, _ ->
                    val sentMessageRequest = documentSnapshot?.documents

                    sentMessagesRequestList.clear()

                    val mergeAdapter = MergeAdapter()
                    recyclerViewAdapter.adapter = mergeAdapter
                    val adapter = recyclerViewAdapter.adapter

                    for (message in sentMessageRequest!!) {
                        val sendMessagesHashMap = hashMapOf<String, Any>()
                        sendMessagesHashMap["message"] = message["message"].toString()
                        sendMessagesHashMap["time"] = message["time"].toString()
                        sentMessagesRequestList.add(sendMessagesHashMap)
                    }

                    // Устанавливает адаптер, на полученные сообщения
                    mergeAdapter.addAdapter(SentMessagesRecyclerView(sentMessagesRequestList))
                    adapter?.notifyDataSetChanged()
                }
        }
    }
}
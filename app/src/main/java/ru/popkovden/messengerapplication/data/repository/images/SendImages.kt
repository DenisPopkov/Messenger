package ru.popkovden.messengerapplication.data.repository.images

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.SentImageModel
import ru.popkovden.messengerapplication.utils.helper.getData.getCollectionSize

object SendImages {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = firebaseStorage.reference.child("filesFromChat")
    private var reference: StorageReference? = null

    private var sentImages: HashMap<String, Any> = hashMapOf()
    private var receivedImages: HashMap<String, Any> = hashMapOf()
    private val imageHelper = arrayListOf<String>()

    fun sendImages(UID: String, UserUID: String, sentImageModel: SentImageModel) = CoroutineScope(IO).launch {

        sentImages.clear()
        receivedImages.clear()

        sentImageModel.image.forEach {images ->
            reference = storageReference.child("$UID-${sentImageModel.secretName}").child("image - ${System.currentTimeMillis()}")
            reference?.putFile(Uri.parse(images))?.addOnSuccessListener {

                val result = it.metadata!!.reference!!.downloadUrl

                result.addOnSuccessListener { uri ->

                    imageHelper.add(uri.toString())
                }.addOnCompleteListener {
                    if (sentImageModel.image.size == imageHelper.size) {
                        sendImage(UID, UserUID, sentImageModel)
                    }
                }
            }
        }
    }

    private fun sendImage(UID: String, UserUID: String, sentImageModel: SentImageModel)= CoroutineScope(IO).launch {
        // Наполняет данные для "отправленного" сообщения"
        sentImages["image"] = sentImageModel.image
        sentImages["time"] = sentImageModel.time
        sentImages["id"] = getCollectionSize(UID, UserUID)!!
        sentImages["CONTENT_TYPE"] = 3

        // Наполняет данные для "полученного" другим пользователем сообщением
        receivedImages["image"] = sentImageModel.image
        receivedImages["time"] = sentImageModel.time
        receivedImages["id"] = getCollectionSize(UID, UserUID)!!
        receivedImages["CONTENT_TYPE"] = 4

        // Отправляет в БД
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("sentImages").add(sentImages)

        firebaseFirestore.collection("users").document(UserUID)
            .collection("chats").document(UID).collection("receivedImages").add(receivedImages)
    }
}
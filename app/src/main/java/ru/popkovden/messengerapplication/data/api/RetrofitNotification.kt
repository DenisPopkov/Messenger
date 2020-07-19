package ru.popkovden.messengerapplication.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.popkovden.messengerapplication.model.notification.PushNotificationModel
import ru.popkovden.messengerapplication.utils.helper.ConstantsNotification.Companion.CONTENT_TYPE
import ru.popkovden.messengerapplication.utils.helper.ConstantsNotification.Companion.SERVER_KEY

interface RetrofitNotification {

    @Headers("Authorization:key=$SERVER_KEY", "Content-TYPE:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(@Body notification: PushNotificationModel) : Response<ResponseBody>
}
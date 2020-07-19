package ru.popkovden.messengerapplication.data.repository.notification

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.popkovden.messengerapplication.data.api.RetrofitNotification
import ru.popkovden.messengerapplication.utils.helper.ConstantsNotification.Companion.BASE_URL

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .build()
        }

        val api by lazy {
            retrofit.create(RetrofitNotification::class.java)
        }
    }
}
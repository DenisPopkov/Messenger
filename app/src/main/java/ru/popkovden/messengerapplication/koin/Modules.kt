package ru.popkovden.messengerapplication.koin

import android.app.Activity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.popkovden.messengerapplication.data.repository.auth.CheckIfUserExist
import ru.popkovden.messengerapplication.data.repository.auth.CreateUser
import ru.popkovden.messengerapplication.data.repository.auth.FirebaseAuthHelper
import ru.popkovden.messengerapplication.data.repository.contacts.GetContacts
import ru.popkovden.messengerapplication.data.repository.images.SendImages
import ru.popkovden.messengerapplication.data.repository.messages.GetMessages
import ru.popkovden.messengerapplication.data.repository.messages.SendMessageToUser
import ru.popkovden.messengerapplication.data.repository.posts.CreatePost
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.utils.customView.FabControl
import ru.popkovden.messengerapplication.utils.customView.SnackBarView
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.DecodeUri
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.*

val helperModule = module {
    single { FirebaseAuthHelper }
    single { SnackBarView }
    single { StatusBarColorChanger }
    single { FabControl }
    single { CreateUser }
    single { DecodeUri }
    single { InfoAboutUser }
    single { SendImages }
}

val repositoryModule = module {
    single { GetPosts }
    single { CreatePost }
    single { SendMessageToUser }
    factory { GetMessages }
    single { CheckIfUserExist }
    single { GetContacts }
}

val viewModelModule = module {
    viewModel { VerifyCodeFragmentViewModel() }
    viewModel { SendPhoneNumberFragmentViewModel() }
    viewModel { GreetingFragmentViewModel() }
    viewModel { (UID: String)  -> UserProfileFragmentViewModel(UID)}
    viewModel { MessengerFragmentViewModel() }
    viewModel { EditProfileFragmentViewModel() }
    viewModel { (UID: String, minute: String, hour: String, day: String) -> ChatScreenFragmentViewModel(UID, minute, hour, day) }
    viewModel { (activity: Activity) -> ContactsFragmentViewModel(activity)}
}
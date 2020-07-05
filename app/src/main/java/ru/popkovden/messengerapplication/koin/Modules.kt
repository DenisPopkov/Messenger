package ru.popkovden.messengerapplication.koin

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.popkovden.messengerapplication.data.repository.auth.CreateUser
import ru.popkovden.messengerapplication.data.repository.auth.FirebaseAuthHelper
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
    single { CreateUser() }
    single { DecodeUri }
    single { InfoAboutUser }
}

val repositoryModule = module {
    single { GetPosts }
    single { CreatePost }
    single { SendMessageToUser }
    factory { GetMessages() }
}

val viewModelModule = module {
    viewModel { VerifyCodeFragmentViewModel() }
    viewModel { SendPhoneNumberFragmentViewModel() }
    viewModel { GreetingFragmentViewModel() }
    viewModel { UserProfileFragmentViewModel() }
    viewModel { MessengerFragmentViewModel() }
    viewModel { EditProfileFragmentViewModel() }
}
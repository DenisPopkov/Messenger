package ru.popkovden.messengerapplication.koin

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.popkovden.messengerapplication.data.repository.auth.CreateUser
import ru.popkovden.messengerapplication.data.repository.auth.FirebaseAuthHelper
import ru.popkovden.messengerapplication.data.repository.posts.CreatePost
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.utils.customView.FabControl
import ru.popkovden.messengerapplication.utils.customView.SnackBarView
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.DecodeUri
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.GreetingFragmentViewModel
import ru.popkovden.messengerapplication.viewmodel.SendPhoneNumberFragmentViewModel
import ru.popkovden.messengerapplication.viewmodel.VerifyCodeFragmentViewModel

val helperModule = module {
    single { FirebaseAuthHelper }
    single { SnackBarView }
    single { StatusBarColorChanger }
    single { FabControl }
    single { CreateUser() }
    single { DecodeUri }
    single { CreatePost }
    single { InfoAboutUser }
    single { GetPosts }
}

val viewModelModule = module {
    viewModel { VerifyCodeFragmentViewModel() }
    viewModel { SendPhoneNumberFragmentViewModel() }
    viewModel { GreetingFragmentViewModel() }
}
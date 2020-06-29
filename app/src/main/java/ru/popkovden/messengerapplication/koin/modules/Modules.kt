package ru.popkovden.messengerapplication.koin.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.popkovden.messengerapplication.data.repository.CreateUser
import ru.popkovden.messengerapplication.utils.auth.FirebaseAuthHelper
import ru.popkovden.messengerapplication.utils.custom_view.FabControl
import ru.popkovden.messengerapplication.utils.custom_view.SnackBarView
import ru.popkovden.messengerapplication.utils.custom_view.StatusBarColorChanger
import ru.popkovden.messengerapplication.viewmodel.SendPhoneNumberFragmentViewModel
import ru.popkovden.messengerapplication.viewmodel.VerifyCodeFragmentViewModel

val helperModule = module {
    single { FirebaseAuthHelper }
    single { SnackBarView }
    single { StatusBarColorChanger }
    single { FabControl }
    single { CreateUser() }
}

val viewModelModule = module {
    viewModel { VerifyCodeFragmentViewModel() }
    viewModel { SendPhoneNumberFragmentViewModel() }
}
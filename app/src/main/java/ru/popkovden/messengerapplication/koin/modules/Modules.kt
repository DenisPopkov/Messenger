package ru.popkovden.messengerapplication.koin.modules

import androidx.appcompat.widget.AppCompatEditText
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.popkovden.messengerapplication.viewmodel.SendPhoneNumberFragmentViewModel
import ru.popkovden.messengerapplication.viewmodel.VerifyCodeFragmentViewModel

val viewModelModule = module {
    viewModel { VerifyCodeFragmentViewModel() }
    viewModel { SendPhoneNumberFragmentViewModel() }
}
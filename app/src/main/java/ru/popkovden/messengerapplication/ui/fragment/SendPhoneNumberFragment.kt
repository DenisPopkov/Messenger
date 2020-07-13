package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentSendPhoneNumberBinding
import ru.popkovden.messengerapplication.viewmodel.SendPhoneNumberFragmentViewModel

class SendPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentSendPhoneNumberBinding
    private val viewModel: SendPhoneNumberFragmentViewModel by viewModel()
//    private val customView: SnackBarView by inject()
//    private val checkInternetConnection: CheckInternetConnection by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_phone_number, container, false)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        arguments?.let {
            val phone = SendPhoneNumberFragmentArgs.fromBundle(it).phoneNumber
            viewModel.update(phone)
            binding.phoneNumber.setText(phone)
        }

        binding.phoneNumber.setText("+7")

        CoroutineScope(Main).launch {
            while (true) {
                val phoneNumber = binding.phoneNumber.text.toString()
                delay(500)
                viewModel.update(phoneNumber)
            }
        }

        binding.toVerifyPhoneNumber.setOnClickListener {
            val phoneNumber = binding.phoneNumber.text.toString()
            val action = SendPhoneNumberFragmentDirections.actionSendPhoneNumberFragmentToVerifyCodeFragment(phoneNumber)
            findNavController().navigate(action)
        }

        return binding.root
    }
}
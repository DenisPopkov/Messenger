package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentSendPhoneNumberBinding
import ru.popkovden.messengerapplication.utils.custom_view.SnackBarView
import ru.popkovden.messengerapplication.utils.internet_checker.CheckInternetConnection
import ru.popkovden.messengerapplication.viewmodel.SendPhoneNumberFragmentViewModel

class SendPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentSendPhoneNumberBinding
    private val viewModel: SendPhoneNumberFragmentViewModel by viewModel()
    private val customView: SnackBarView by inject()
    private val checkInternetConnection = CheckInternetConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            binding =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_send_phone_number,
                    container,
                    false
                )
        } catch (e: Exception) {
            Log.d("efefe", "error - $e")
            throw e
        }

        arguments?.let {
            val phone = SendPhoneNumberFragmentArgs.fromBundle(it).phoneNumber
            viewModel.update(phone)
            binding.phoneNumber.setText(phone)
        }

        CoroutineScope(Main).launch {
            while (true) {
                val phoneNumber = binding.phoneNumber.text.toString()
                delay(500)
                viewModel.update(phoneNumber)
            }
        }

        binding.toVerifyPhoneNumber.setOnClickListener {
            val phoneNumber = binding.phoneNumber.text.toString()

            if (checkInternetConnection.isInternetAvailable(container?.context!!)) {

                val action =
                    SendPhoneNumberFragmentDirections.actionSendPhoneNumberFragmentToVerifyCodeFragment(
                        phoneNumber
                    )

                findNavController().navigate(action)
            } else {
                customView.showSnackBar(it, resources.getString(R.string.internet), 5000)
            }
        }

        viewModel.currentPhoneNumber.observe(viewLifecycleOwner, Observer { number ->

            if (number.contains("+") && number.length > 5) {

                with(binding.statusIcon) {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.success)
                }

                binding.toVerifyPhoneNumber.isEnabled = true
                binding.statusIcon.visibility = View.VISIBLE

            } else if (number.isNotEmpty() && !number.contains("+")) {

                with(binding.statusIcon) {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.error_icon)
                }

                binding.toVerifyPhoneNumber.isEnabled = false
            }
        })

        return binding.root
    }
}
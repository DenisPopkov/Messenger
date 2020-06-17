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
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentSendPhoneNumberBinding
import ru.popkovden.messengerapplication.viewmodel.SendPhoneNumberFragmentViewModel

class SendPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentSendPhoneNumberBinding
    private val viewModel: SendPhoneNumberFragmentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_phone_number, container, false)

        arguments?.let {
            val phone = SendPhoneNumberFragmentArgs.fromBundle(it)
            binding.phoneNumber.setText(phone.toString())
            Log.d("efefe", phone.toString())
        }

        binding.toVerifyPhoneNumber.setOnClickListener {
            val phoneNumber = binding.phoneNumber.text.toString()
            findNavController().navigate(SendPhoneNumberFragmentDirections.actionSendPhoneNumberFragmentToVerifyCodeFragment(phoneNumber))
        }

        viewModel.currentPhoneNumber.observe(viewLifecycleOwner, Observer {number ->
            binding.phoneNumber.setText(number)
            if (number.toString().contains("+")) Log.d("efefe", "everything ok") else Log.d("efefe", "not")
            Log.d("efefe", number.toString())
        })

        return binding.root
    }
}
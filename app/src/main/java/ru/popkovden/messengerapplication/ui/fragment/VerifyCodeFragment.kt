package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_send_phone_number.*
import kotlinx.android.synthetic.main.fragment_verify_code.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentVerifyCodeBinding
import ru.popkovden.messengerapplication.utils.text_watcher_for_verify_code.RequestFocusToNextView
import ru.popkovden.messengerapplication.utils.text_watcher_for_verify_code.RequestFocusToPreviousView
import ru.popkovden.messengerapplication.viewmodel.VerifyCodeFragmentViewModel

class VerifyCodeFragment : Fragment() {

    private var phoneNumber = ""
    private val viewModel: VerifyCodeFragmentViewModel by viewModel()
    private lateinit var binding: FragmentVerifyCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verify_code, container, false)

        binding.backToSendPhoneNumber?.setOnClickListener {
            val action = VerifyCodeFragmentDirections.actionVerifyCodeFragmentToSendPhoneNumberFragment(phoneNumber, emptyArray())
            findNavController().navigate(action)
        }

        arguments?.let {
            phoneNumber = VerifyCodeFragmentArgs.fromBundle(it).phoneNumber
        }

        //Переходит на новый editText
        binding.firstCodeBox.addTextChangedListener(RequestFocusToNextView(binding.firstCodeBox, binding.secondCodeBox))
        binding.secondCodeBox.addTextChangedListener(RequestFocusToNextView(binding.secondCodeBox, binding.thirdCodeBox))
        binding.thirdCodeBox.addTextChangedListener(RequestFocusToNextView(binding.thirdCodeBox, binding.fourthCodeBox))
        binding.fourthCodeBox.addTextChangedListener(RequestFocusToNextView(binding.fourthCodeBox, binding.fifthCodeBox))
        binding.fifthCodeBox.addTextChangedListener(RequestFocusToNextView(binding.fifthCodeBox, binding.sixthCodeBox))
        binding.sixthCodeBox.addTextChangedListener(RequestFocusToNextView(binding.sixthCodeBox, null))

        //Возращает на предыдущий editText
        binding.firstCodeBox.setOnKeyListener(RequestFocusToPreviousView(binding.firstCodeBox, null))
        binding.secondCodeBox.setOnKeyListener(RequestFocusToPreviousView(binding.secondCodeBox, binding.firstCodeBox))
        binding.thirdCodeBox.setOnKeyListener(RequestFocusToPreviousView(binding.thirdCodeBox, binding.secondCodeBox))
        binding.fourthCodeBox.setOnKeyListener(RequestFocusToPreviousView(binding.fourthCodeBox, binding.thirdCodeBox))
        binding.fifthCodeBox.setOnKeyListener(RequestFocusToPreviousView(binding.fifthCodeBox, binding.fourthCodeBox))
        binding.sixthCodeBox.setOnKeyListener(RequestFocusToPreviousView(binding.sixthCodeBox, binding.fifthCodeBox))

        binding.messageForUser.text = getString(R.string.message_in_fragment_verify_screen, phoneNumber)

        binding.timer.isEnabled = false
        binding.timer.setTextColor(resources.getColor(R.color.grayColor))

        viewModel.currentLiveDataTimerCodeResend.observe(viewLifecycleOwner, Observer { time ->
            binding.timer.text = getString(R.string.resend, time.toString())

            if (time == 0) {
                binding.timer.setTextColor(resources.getColor(R.color.whiteColor))
                binding.timer.text = getString(R.string.resendWithoutTimer)
                binding.timer.isEnabled = true
            }
        })

        binding.timer.setOnClickListener {
            viewModel.verifyCode()
        }

        return binding.root
    }
}
package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentVerifyCodeBinding
import ru.popkovden.messengerapplication.utils.auth.FirebaseAuthHelper
import ru.popkovden.messengerapplication.viewmodel.VerifyCodeFragmentViewModel

class VerifyCodeFragment : Fragment() {

    private var number = ""
    private var verificationId = ""
    private var code = ""
    private var uid = ""
    private var infoFromSms = hashMapOf<String, String>()
    private val viewModel: VerifyCodeFragmentViewModel by viewModel()
    private val firebaseAuthHelper: FirebaseAuthHelper by inject()
    private val job = Job()
    private val coroutineScope = CoroutineScope(Main + job)
    private lateinit var binding: FragmentVerifyCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verify_code, container, false)

        binding.backToSendPhoneNumber?.setOnClickListener {
            findNavController().navigate(VerifyCodeFragmentDirections.actionVerifyCodeFragmentToSendPhoneNumberFragment(number))
        }

        arguments?.let {
            with(VerifyCodeFragmentArgs.fromBundle(it)) {
                number = phoneNumber
                infoFromSms = firebaseAuthHelper.sendSmsCode(phoneNumber)
            }

            coroutineScope.launch {

                while (true) {
                    delay(500)
                    code = infoFromSms["smsCode"].toString()
                    verificationId = infoFromSms["verificationId"].toString()

                    if (code.length >= 6) {
                        fillCodeBox()
                        firebaseAuthHelper.signIn(verificationId, code)
                        delay(1500)
                        this.cancel()
                        val action = VerifyCodeFragmentDirections.actionVerifyCodeFragmentToGreetingFragment(number)
                        findNavController().navigate(action)
                    }
                }
            }
        }

        binding.messageForUser.text = getString(R.string.message_in_fragment_verify_screen, number)

        viewModel.currentLiveDataTimerCodeResend.observe(viewLifecycleOwner, Observer { time ->
            binding.timer.text = getString(R.string.resend, time.toString())

            if (time == 0) {
                binding.timer.setTextColor(resources.getColor(R.color.whiteColor))
                binding.timer.text = getString(R.string.resendWithoutTimer)
                binding.timer.isEnabled = true
            }
        })

        binding.timer.setOnClickListener {
            makeTimerReset()
        }

        return binding.root
    }

    private fun fillCodeBox() {
        binding.firstCodeBox.setText(code[0].toString())
        binding.secondCodeBox.setText(code[1].toString())
        binding.thirdCodeBox.setText(code[2].toString())
        binding.fourthCodeBox.setText(code[3].toString())
        binding.fifthCodeBox.setText(code[4].toString())
        binding.sixthCodeBox.setText(code[5].toString())
    }

    private fun cleanCodeBox() {
        binding.firstCodeBox.setText("")
        binding.secondCodeBox.setText("")
        binding.thirdCodeBox.setText("")
        binding.fourthCodeBox.setText("")
        binding.fifthCodeBox.setText("")
        binding.sixthCodeBox.setText("")
    }

    private fun makeTimerReset() {  // TODO() ЗДЕСЬ БАГ
        firebaseAuthHelper.sendSmsCode(number)
        viewModel.timerReset()
        binding.timer.isEnabled = false
        binding.timer.setTextColor(resources.getColor(R.color.grayColor))
        verificationId = ""
        code = ""
        cleanCodeBox()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
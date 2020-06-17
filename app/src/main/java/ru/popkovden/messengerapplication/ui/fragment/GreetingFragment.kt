package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentGreetingBinding

class GreetingFragment : Fragment() {

    private lateinit var binding: FragmentGreetingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_greeting, container, false)


        binding.button.setOnClickListener {
            findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToSendPhoneNumberFragment("", emptyArray()))
        }
        return binding.root
    }
}
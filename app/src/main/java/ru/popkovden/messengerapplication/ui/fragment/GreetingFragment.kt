package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentGreetingBinding

class GreetingFragment : Fragment() {

    private lateinit var binding: FragmentGreetingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_greeting, container, false)

        binding.button.setOnClickListener {
            findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToMainChatScreenFragment())
        }

        return binding.root
    }
}
package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.bind
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentMainChatScreenBinding

class MainChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainChatScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_chat_screen, container, false)

        return binding.root
    }
}
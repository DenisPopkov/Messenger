package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentUserProfileBinding
import ru.popkovden.messengerapplication.ui.adapters.profile.MainProfileRecyclerViewPart
import ru.popkovden.messengerapplication.ui.adapters.profile.PostsProfileRecyclerView
import ru.popkovden.messengerapplication.utils.custom_view.FabControl
import ru.popkovden.messengerapplication.utils.custom_view.StatusBarColorChanger

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val uiHelper: StatusBarColorChanger by inject()
    private val fabControl: FabControl by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        uiHelper.changeStatusBarColor(requireActivity())

        val mergerAdapter = MergeAdapter(MainProfileRecyclerViewPart(requireContext()), PostsProfileRecyclerView(requireContext(),
            arrayListOf("Здесь пока ничего нет", "Здесь пока ничего нет", "Здесь пока ничего нет", "Здесь пока ничего нет")))

        binding.profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRecyclerView.adapter = mergerAdapter

        fabControl.controlFabActionPosition(binding.profileRecyclerView, binding.fab)
        return binding.root
    }
}
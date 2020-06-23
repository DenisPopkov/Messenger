package ru.popkovden.messengerapplication.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.popkovden.messengerapplication.ui.fragment.ChatScreenFragment
import ru.popkovden.messengerapplication.ui.fragment.FeedScreenFragment
import ru.popkovden.messengerapplication.ui.fragment.MainChatScreenFragment
import ru.popkovden.messengerapplication.ui.fragment.UserProfileFragment


class MainChatScreenViewPagerAdapter(activity: Fragment) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> UserProfileFragment()
            1 -> ChatScreenFragment()
            2 -> FeedScreenFragment()
            else -> UserProfileFragment()
        }
    }
}
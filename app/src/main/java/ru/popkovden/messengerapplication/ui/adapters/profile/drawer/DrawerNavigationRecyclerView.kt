package ru.popkovden.messengerapplication.ui.adapters.profile.drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.drawer_items.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.DrawerItemsModel
import ru.popkovden.messengerapplication.ui.fragment.UserProfileFragmentDirections
import ru.popkovden.messengerapplication.utils.darkMode.enableDarkMode
import ru.popkovden.messengerapplication.utils.darkMode.loadInfoAboutMode

class DrawerNavigationRecyclerView(private val drawerList: ArrayList<DrawerItemsModel>, val name: String, val userImage: String) : RecyclerView.Adapter<DrawerNavigationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerNavigationViewHolder = DrawerNavigationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.drawer_items, parent, false))

    override fun getItemCount(): Int = drawerList.size
    override fun onBindViewHolder(holder: DrawerNavigationViewHolder, position: Int) = holder.itemView.run {

        val current = drawerList[position]
        edit_icon.setImageResource(current.icon)
        edit_icon.background = current.background
        edit_text.text = current.text

        this.setOnClickListener {

            val action = UserProfileFragmentDirections.actionAccountToEditProfileFragment(name, userImage)

            when (position) {
                0 -> findNavController().navigate(action)
                1 -> {
                    loadInfoAboutMode(context)
                    enableDarkMode(context)
                }
            }
        }
    }
}

class DrawerNavigationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
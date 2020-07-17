package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.profile_info.view.*
import kotlinx.android.synthetic.main.profile_recyclerview_part.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.utils.helper.getData.getCountOfPosts
import ru.popkovden.messengerapplication.utils.helper.getData.getFriendsCount
import ru.popkovden.messengerapplication.utils.helper.getData.getPhotoCount

class MainProfileRecyclerViewPart(val context: Context, val image: String, val name: String, val UID: String) : RecyclerView.Adapter<MainProfileViewHolder>() {

    private var postsCount: Int = 0
    private var friendsCount: Int = 0
    private var photosCount: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProfileViewHolder =
        MainProfileViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_recyclerview_part, parent, false))

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: MainProfileViewHolder, position: Int) = holder.itemView.run {

        CoroutineScope(Dispatchers.Main).launch {
            postsCount = getCountOfPosts(UID) // Получение количества постов
            friendsCount = getFriendsCount(UID) // Получение количества друзей
            photosCount = getPhotoCount(UID) // Получение количества друзей

            post_count.text = postsCount.toString()
            friends_count.text = friendsCount.toString()
            photos_count.text = photosCount.toString()
        }

        Glide.with(context).load(image).into(profileImage)
        profileName.text = name
    }
}

class MainProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
package ru.popkovden.messengerapplication.ui.adapters.chat.messeges

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.messenger_images.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.ui.fragment.FragmentMessengerScreenDirections

class ImagesViewPager(private val imagesSliderList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<MessengerImages>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessengerImages =
        MessengerImages(LayoutInflater.from(parent.context).inflate(R.layout.messenger_images, parent, false))

    override fun getItemCount(): Int = imagesSliderList.size

    override fun onBindViewHolder(holder: MessengerImages, position: Int) = holder.itemView.run {

        val currentPosition = imagesSliderList[position]
        Glide.with(context).load(currentPosition).placeholder(R.drawable.placeholder).into(sentImage)

        indicatorCounter.visibility = View.GONE

        if (imagesSliderList.size > 1) {
            indicatorCounter.visibility = View.VISIBLE
            indicatorCounter.text = "${position.plus(1)}/${imagesSliderList.size}"
        }

        sentImage.setOnClickListener {
            Navigation.findNavController(it).navigate(FragmentMessengerScreenDirections.actionMessengerToZoomImagesFragment(imagesSliderList.toTypedArray(), position))
        }
    }
}

class MessengerImages(itemView: View) : RecyclerView.ViewHolder(itemView)
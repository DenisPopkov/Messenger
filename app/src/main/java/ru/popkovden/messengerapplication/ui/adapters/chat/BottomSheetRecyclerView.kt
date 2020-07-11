package ru.popkovden.messengerapplication.ui.adapters.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.drawer_items.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.ChatBottomSheetModel

class BottomSheetRecyclerView(val arrayList: ArrayList<ChatBottomSheetModel>) : RecyclerView.Adapter<BottomSheetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder = BottomSheetViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_chat_item, parent, false))

    override fun getItemCount(): Int = arrayList.size
    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) = holder.itemView.run {

        edit_icon.setImageDrawable(arrayList[position].icon)
        edit_text.text = arrayList[position].text
        edit_icon.background = arrayList[position].backgroundColor
    }
}

class BottomSheetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
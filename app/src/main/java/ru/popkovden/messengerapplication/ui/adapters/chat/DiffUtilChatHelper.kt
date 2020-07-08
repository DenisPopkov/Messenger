package ru.popkovden.messengerapplication.ui.adapters.chat

import androidx.recyclerview.widget.DiffUtil

class DiffUtilChatHelper(private val oldList: ArrayList<HashMap<String, Any>>, private val newList: ArrayList<HashMap<String, Any>>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition]["id"] == newList[newItemPosition]["id"]
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}
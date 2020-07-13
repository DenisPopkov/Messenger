package ru.popkovden.messengerapplication.ui.adapters.chat.helper

import androidx.recyclerview.widget.DiffUtil
import ru.popkovden.messengerapplication.model.MessageModel

class DiffUtilChatHelper(private val oldList: List<MessageModel>, private val newList: List<MessageModel>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
}
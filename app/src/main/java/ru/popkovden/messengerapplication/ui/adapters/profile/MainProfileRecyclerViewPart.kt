package ru.popkovden.messengerapplication.ui.adapters.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class MainProfileRecyclerViewPart(val context: Context) : RecyclerView.Adapter<MainProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProfileViewHolder =
        MainProfileViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_recyclerview_part, parent, false))

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: MainProfileViewHolder, position: Int) {}
}

class MainProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
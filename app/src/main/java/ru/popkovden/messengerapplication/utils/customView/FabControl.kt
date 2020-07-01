package ru.popkovden.messengerapplication.utils.customView

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

object FabControl {

    fun controlFabActionPosition(recyclerView: RecyclerView, fab: FloatingActionButton) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && fab.isShown) {
                    fab.hide()
                } else if(dy < 0 && !fab.isShown) {
                    fab.show()
                }
            }
        })
    }
}
package com.medtracker

import android.view.View


interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, user: com.medtracker.model.User){
    }
}
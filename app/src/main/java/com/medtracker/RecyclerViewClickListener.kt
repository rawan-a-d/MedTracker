package com.medtracker

import android.view.View

/* Use generic to be able to user this Recycler for all pages */
interface RecyclerViewClickListener<in T> {
    fun onRecyclerViewItemClicked(view: View, obj: T){
    }
}
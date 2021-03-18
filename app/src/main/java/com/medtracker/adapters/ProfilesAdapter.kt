package com.medtracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medtracker.R
import com.medtracker.interfaces.RecyclerViewClickListener
import com.medtracker.models.User
import kotlinx.android.synthetic.main.recycler_view_profile.view.*

class ProfilesAdapter : RecyclerView.Adapter<ProfilesAdapter.ProfileViewModel>() {

    private var users = mutableListOf<User>()
    var listener: RecyclerViewClickListener<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_profile, parent, false)
    )

    override fun getItemCount() = users.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProfileViewModel, position: Int) {
        holder.view.text_view_name.text = users[position].firstname + " " + users[position].lastname

        // on click name of profile
        holder.view.text_view_name_container.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, users[position])
        }
    }


    fun setProfiles(users: List<User>) {
        this.users = users as MutableList<User>
        // display users by notifying the recycler view
        notifyDataSetChanged()
    }


    // add user to view
    fun addProfile(user: User) {
        if (!users.contains(user)) {
            users.add(user)
        } else {
            val index = users.indexOf(user)

                users[index] = user
        }
        notifyDataSetChanged()
    }

    class ProfileViewModel(val view: View) : RecyclerView.ViewHolder(view)
}
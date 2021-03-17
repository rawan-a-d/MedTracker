package com.medtracker.choose_profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.medtracker.*
import com.medtracker.home.MainActivity
import com.medtracker.model.Shared_Prefs
import com.medtracker.model.User
import kotlinx.android.synthetic.main.fragment_profiles.*
import kotlinx.android.synthetic.main.recycler_view_profile.*
import kotlinx.android.synthetic.main.recycler_view_profile.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProfilesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilesFragment : Fragment(), RecyclerViewClickListener<User> {
    private lateinit var viewModel: ProfilesViewModel
    private val adapter = ProfilesAdapter()
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfilesViewModel::class.java)

        // Shared preferences
        prefs = PreferenceHelper.customPrefs(requireContext(), Shared_Prefs)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiles, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_profiles.adapter = adapter


        // get current user id
        val id = prefs.getString("id", null)

        // fetch profiles
        viewModel.fetchProfiles(id)


        // subscribe to get all profiles
        viewModel.users.observe(viewLifecycleOwner, {
            // show profiles
            adapter.setProfiles(it)
        })

        // subscribe to add new user
        viewModel.user.observe(viewLifecycleOwner, {
            adapter.addProfile(it)
        })

    }


    // triggered when a click event is triggered (for example click on name of user)
    override fun onRecyclerViewItemClicked(
        view: View,
        user: User
    ) {
        when (view.id) {
            // profile name
            R.id.text_view_name_container -> {
                // Shared preferences
                val editor = prefs.edit()

                // add data to shared preferences
                // save current profile to shared preferences
                editor.apply {
                    putString("currentProfileId", user.id)
                    // finish and write to shared preferences
                    apply()
                }

                // Redirect to home page
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
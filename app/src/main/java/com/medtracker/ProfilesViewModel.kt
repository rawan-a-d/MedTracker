package com.medtracker


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.model.NODE_USERS
import com.medtracker.model.Shared_Prefs
import com.medtracker.model.User
import java.lang.Exception

// Handles database
class ProfilesViewModel : ViewModel() {
    // db connection, reference
    private val dbUsers = FirebaseFirestore.getInstance().collection(NODE_USERS)

    // users list
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    // new user
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    // result of the task with the db
    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result


    // listen to changes of individual node (user)
    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {}

        // on update
        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val user = snapshot.getValue(User::class.java)
            user?.id = snapshot.key
            _user.value = user
        }

        // on add
        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val user = snapshot.getValue(User::class.java)
            user?.id = snapshot.key
            _user.value = user
        }

        // on delete
        override fun onChildRemoved(snapshot: DataSnapshot) {
            val user = snapshot.getValue(User::class.java)
            user?.id = snapshot.key
            _user.value = user
        }
    }


    // get all profiles
    fun fetchProfiles(id: String?) {
        // get id of current user from share preferences
        dbUsers.document(id!!).get()
            .addOnSuccessListener { document ->
                val users = mutableListOf<User>()
                val user = document.toObject(User::class.java)
                user?.id = id
                val children = user?.children

                if (user != null) {
                    users.add(user)
                }

                if (children != null) {
                    for (child in children) {
                        users.add(child)
                    }
                }
                _users.value = users

            }
            .addOnFailureListener {
            }
    }
}
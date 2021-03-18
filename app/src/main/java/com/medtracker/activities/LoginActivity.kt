package com.medtracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.utils.PreferenceHelper
import com.medtracker.R
import com.medtracker.models.NODE_USERS
import com.medtracker.models.Shared_Prefs
import com.medtracker.models.User

class LoginActivity : AppCompatActivity() {
    private val dbUsers = FirebaseFirestore.getInstance().collection(NODE_USERS)
    //lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Shared preferences
        val prefs = PreferenceHelper.customPrefs(this, Shared_Prefs)
        val editor = prefs.edit()

        // get credentials
        val username = prefs.getString("email", null)
        val password = prefs.getString("password", null)
        val type = prefs.getString("type", null)


        if(username != null && password != null) {
            if(type == "patient") {
                // redirect to choose profile
                val intent = Intent(this, ChooseProfileActivity::class.java)
                startActivity(intent)
            }
            else if(type == "delivery") {
                // redirect to scanner page
                val intent = Intent(this, ScannerActivity::class.java)
                startActivity(intent)
            }


        }

        // if no data in shared preferences, login screen
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextEmail)
            val pass = findViewById<EditText>(R.id.editTextPassword)

            when {
                // if email is empty
                TextUtils.isEmpty(email.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // if password is empty
                TextUtils.isEmpty(pass.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // else
                else -> {
                    val emailText = email.text.toString().trim { it <= ' '}
                    val passText = pass.text.toString().trim { it <= ' '}
                    var id: String

                    // create an instance and register a user with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passText)
                        .addOnCompleteListener( // when completed
                            OnCompleteListener<AuthResult> { task ->

                                // if the login is successfully done
                                if(task.isSuccessful) {
                                    // get user id
                                    id = FirebaseAuth.getInstance().currentUser.uid

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You were logged in successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                   // fetchUser(id)
                                    dbUsers.document(id!!).get()
                                        .addOnSuccessListener { document ->
                                            val user = document.toObject(User::class.java)
                                            user?.id = id

                                            //user = userObj!!

                                            // add data to shared preferences
                                            editor.apply {
                                                putString("email", emailText)
                                                putString("password", passText)
                                                putString("id", id)
                                                putString("type", user?.type)
                                                // finish and write to shared preferences
                                                apply()
                                            }


                                            // if patient
                                            if(user?.type == "patient") {
                                                // redirect to choose profile page
                                                val intent = Intent(this, ChooseProfileActivity::class.java)
                                                startActivity(intent)
                                            }
                                            // if delivery
                                            else if(user?.type == "delivery") {
                                                // redirect to scanner page
                                                val intent = Intent(this, ScannerActivity::class.java)
                                                startActivity(intent)
                                            }
                                        }
                                        .addOnFailureListener {
                                        }


                                }
                                else {
                                    // If the login is not successful then show error message
                                    Toast.makeText( // print exception
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                }
            }
        }


    }

    // get all profiles
/*    private fun fetchUser(id: String?) {
        println("Getting user")

        dbUsers.document(id!!).get()
            .addOnSuccessListener { document ->
                val userObj = document.toObject(User::class.java)
                userObj?.id = id

                println("User " + userObj)

                user = userObj!!
            }
            .addOnFailureListener {
            }
    }*/
}
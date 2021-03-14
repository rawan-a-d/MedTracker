package com.medtracker

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
import com.medtracker.model.Shared_Prefs

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Shared preferences
        val prefs = PreferenceHelper.customPrefs(this, Shared_Prefs)
        val editor = prefs.edit()

        // get credentials
        val username = prefs.getString("email", null)
        val password = prefs.getString("password", null)

        if(username != null && password != null) {
            // redirect to main
            val intent = Intent(this, ChooseProfileActivity::class.java)
            startActivity(intent)
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

                                    // add data to shared preferences
                                    editor.apply {
                                        putString("email", emailText)
                                        putString("password", passText)
                                        putString("id", id)
                                        // finish and write to shared preferences
                                        apply()
                                    }

                                    // redirect to main page
                                    val intent = Intent(this, ChooseProfileActivity::class.java)
                                    startActivity(intent)
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
}
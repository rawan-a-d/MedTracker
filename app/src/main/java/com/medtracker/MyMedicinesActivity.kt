package com.medtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyMedicinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_medicines)

        //intialize and assign functionalities
        val bottomNavigator = findViewById<BottomNavigationView>(R.id.bottom_navigator)

        //set the selected icon on mymedicines page
//        bottomNavigator.setSelectedItemId(R.id.MyMedicines)
        bottomNavigator.selectedItemId = R.id.myMedicines

        //navigation functionalities
        bottomNavigator.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.myMedicines -> {
//                    val intent = Intent(this, MyMedicinesActivity::class.java)
//                    startActivity(intent)
                    true
                }
                //for the switch profile tab
//                R.id.action_music -> {
//                }
            }
            false
        }
    }
}
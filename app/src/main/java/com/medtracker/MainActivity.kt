package com.medtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //intialize and assign functionalities
        val bottomNavigator = findViewById<BottomNavigationView>(R.id.bottom_navigator)

        //set the selected icon on home page
//        bottomNavigator.setSelectedItemId(R.id.home)
        bottomNavigator.selectedItemId = R.id.home

        //navigation functionalities
        bottomNavigator.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.myMedicines -> {
                    val intent = Intent(this, MyMedicinesActivity::class.java)
                    startActivity(intent)
                    true
                }
                //for the switch profile tab
//                R.id.switchProfiles -> {
//                }
            }
            true
        }
    }
}
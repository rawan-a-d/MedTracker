package com.medtracker.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.R
import com.medtracker.models.Prescription
import com.medtracker.adapters.PrescriptionAdapter


class MyMedicinesActivity : AppCompatActivity() {

    lateinit var listview: ListView
    lateinit var currentMedBtn: Button
    lateinit var previousMedBtn: Button

    var medicinesList: MutableList<Prescription>  = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_medicines)

        GetMyCurrentMedicines()

        listview = findViewById(R.id.medicinesList)
        currentMedBtn = findViewById(R.id.buttonNow)
        previousMedBtn = findViewById(R.id.buttonHistory)

        /* Bottom nav bar*/

        //intialize and assign functionalities
        val bottomNavigator = findViewById<BottomNavigationView>(R.id.bottom_navigator)

        //set the selected icon on mymedicines page
        //bottomNavigator.setSelectedItemId(R.id.MyMedicines)
        bottomNavigator.selectedItemId = R.id.myMedicines

        //navigation functionalities
        bottomNavigator.setOnNavigationItemSelectedListener { `item` ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.myMedicines -> {
                    true
                }
                //for the switch profile tab
                R.id.switchProfiles -> {
                    val intent = Intent(this, ChooseProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
            }
            true
        }

        currentMedBtn.setOnClickListener{
            GetMyCurrentMedicines()
        }

        previousMedBtn.setOnClickListener{
            GetMyOldMedicines()
        }
    }

    private fun GetMyCurrentMedicines() {

        medicinesList.clear()

        val db = FirebaseFirestore.getInstance()
        db.collection("prescription")
            .whereEqualTo("is_done", false)
            .whereEqualTo("user_id", "537JlhdvpMWNqLdB1Gg2ZvrA5dI3")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful){
                    for (med in it.result!!){
                        val medicine = med.toObject(Prescription::class.java)
                        medicinesList.add(medicine)
                    }
                    val adapter = PrescriptionAdapter(
                        applicationContext,
                            R.layout.medicines,
                        medicinesList
                    )
                    listview.adapter = adapter
                }
            }.addOnFailureListener{
                Log.d("Failled", "Failed to get data");
            }
    }

    private fun GetMyOldMedicines() {

        medicinesList.clear()

        val db = FirebaseFirestore.getInstance()
        db.collection("prescription")
            .whereEqualTo("is_done", true)
            .whereEqualTo("user_id", "537JlhdvpMWNqLdB1Gg2ZvrA5dI3")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful){

                    for (med in it.result!!){
                        val medicine = med.toObject(Prescription::class.java)
                        medicinesList.add(medicine)
                    }
                    val adapter = PrescriptionAdapter(
                        applicationContext,
                            R.layout.medicines,
                        medicinesList
                    )
                    listview.adapter = adapter
                }
            }.addOnFailureListener{
                Log.d("Failled", "Failed to get data");
            }

    }


}
package com.medtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.model.Prescription
import com.medtracker.adapter.PrescriptionAdapter
import com.medtracker.handler.QRHandler


class MyMedicinesActivity : AppCompatActivity() {

    lateinit var listview: ListView
    lateinit var currentMedBtn: Button
    lateinit var previousMedBtn: Button

    lateinit var ref: DatabaseReference
    var medicinesList: MutableList<Prescription>  = mutableListOf()

    lateinit var  test: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_medicines)

        GetMyCurrentMedicines()

        ref = FirebaseDatabase.getInstance().getReference("medicines")
        listview = findViewById(R.id.medicinesList)
        currentMedBtn = findViewById(R.id.buttonNow)
        previousMedBtn = findViewById(R.id.buttonHistory)
        test = findViewById(R.id.testy)

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
//                R.id.switchProfiles -> {
//                }
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

//        val db = FirebaseFirestore.getInstance()
//
//        db.collection("medicines")
////                .whereEqualTo("company", "my Company")
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        Log.d("TAG Helooooooo", "${document.id} => ${document.data}")
//                        val adapter = adapter(applicationContext, R.layout.medicines, medicinesList)
//                        listview.adapter = adapter
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("TAG", "Error getting documents: ", exception)
//                }

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
//                        result.append(med.data.getValue("medicine")).append(med.data.getValue("dose"))
//                                .append(med.data.getValue("frequency"))
                        val medicine = med.toObject(Prescription::class.java)
//                        medicinesList.add(result.toString())

                        Log.d("****", medicine.toString())

                        medicinesList.add(medicine)
                    }
                    val adapter = PrescriptionAdapter(
                        applicationContext,
                        R.layout.medicines,
                        medicinesList
                    )
//                  test.text = result
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
//                        result.append(med.data.getValue("dose")).append(med.data.getValue("frequency"))
//                            .append(med.data.getValue("medicine"))
                        val medicine = med.toObject(Prescription::class.java)
//                        medicinesList.add(result.toString())

                        Log.d("*******" , medicine.toString())
                        medicinesList.add(medicine)
                    }
                    val adapter = PrescriptionAdapter(
                        applicationContext,
                        R.layout.medicines,
                        medicinesList
                    )
//                  textView.text = result
                    listview.adapter = adapter
                }
            }.addOnFailureListener{
                Log.d("Failled", "Failed to get data");
            }

    }


}
package com.medtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.model.Prescription
import com.medtracker.model.adapter
import com.medtracker.model.medicine

class MyMedicinesActivity : AppCompatActivity() {

//    //firebase variables
//    private lateinit var database: DatabaseReference
//
//    //android layout
//    private lateinit var listView: ListView
//
//    //array list
//    private var arryList: ArrayList<String> = ArrayList()
//    private lateinit var adapter: ArrayAdapter<String>

//    private lateinit var medicinesList: RecyclerView
//    // CLOUD FIRE STORE
//    private lateinit var fireBaseStore: FirebaseFirestore
//    private var adapter: MedicineFirestoreRecyclerAdapter? = null

    lateinit var view: View

    lateinit var listview: ListView

    lateinit var ref: DatabaseReference
     var medicinesList: MutableList<Prescription>  = mutableListOf()

//    lateinit var  textView: TextView
//    lateinit var txt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_medicines)

        GetMyMedicines()
        ref = FirebaseDatabase.getInstance().getReference("medicines")
        listview = findViewById(R.id.medicinesList)

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
    }

    private fun GetMyMedicines() {

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




        val db = FirebaseFirestore.getInstance()
        db.collection("prescription")
            .whereEqualTo("is_done", false)
            .whereEqualTo("user_id", "2tq1GuRAtwtJCuJtgZeS")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                Log.d("hi","in coplete listner")
                if(it.isSuccessful){
                    Log.d("hi","in sucess complete listner")

                    for (med in it.result!!){
                        result.append(med.data.getValue("medicine_id")).append(med.data.getValue("dose"))
                                .append(med.data.getValue("frequency"))
                        val medicine = med.toObject(Prescription::class.java)
//                        medicinesList.add(result.toString())

                        medicinesList.add(medicine)
                    }
                    val adapter = adapter(applicationContext, R.layout.medicines, medicinesList)
//                  textView.text = result
                    listview.adapter = adapter
                }
            }.addOnFailureListener{
                Log.d("Failled", "Failed to get data");
            }

    }


}
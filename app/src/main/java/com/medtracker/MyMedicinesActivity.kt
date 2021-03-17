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
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.home.MainActivity
import com.medtracker.model.Prescription
import com.medtracker.adapter.PrescriptionAdapter
import com.medtracker.choose_profile.ChooseProfileActivity
import com.medtracker.handler.QRHandler
import kotlinx.android.synthetic.main.medicinedetails.*


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
        test = findViewById(R.id.testy)

        ref.addValueEventListener(object: ValueEventListener {
             var Ranim: String = "test"
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d("RanimSnap", "in medicinres list")
                if (snapshot.exists()){
                    Log.d("RanimSnap", "in medicinres list")
                    for (med in snapshot.children){
                        val medicine = med.getValue(Prescription::class.java)
                        Log.d("RanimSnap", "in medicinres list")
                        if (medicine != null) {
                            Log.d("Ranim", "in medicinres list")
//                            medicinesList.add(medicine)
                        }
                    }

//                    val adapter = adapter(applicationContext, R.layout.medicines, medicinesList)
//                    listview.adapter = adapter

                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.d("RanimError", "in medicinres list")

            }

        })

        /*Show all prescriped medicines*/
//        database = FirebaseDatabase.getInstance().getReference()
//
//        var medList = findViewById<ListView>(R.id.medicinesList)
//
//        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arryList)
//
//        medList.adapter = adapter
//
//        val childEventListener = object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        }
//        database.addChildEventListener(childEventListener)


        //get instance of fire base cloud
//        fireBaseStore = FirebaseFirestore.getInstance()
//
//        /*show medicines*/
//        medicinesList = findViewById(R.id.medicinesList)
//
//        //Query to get medicines
//        val query: Query = fireBaseStore.collection("medicines")
//        //recycler options
//        val options: FirestoreRecyclerOptions<medicineModel> = FirestoreRecyclerOptions.Builder<medicineModel>()
//            .setQuery(query, medicineModel::class.java).build()
//
//        adapter = MedicineFirestoreRecyclerAdapter(options)
////        medicinesList.adapter = adapter
//        medicinesList.setHasFixedSize(true)
//        medicinesList.adapter = adapter

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
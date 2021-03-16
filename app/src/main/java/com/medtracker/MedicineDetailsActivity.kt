package com.medtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.model.Prescription

class MedicineDetailsActivity : AppCompatActivity() {

//    var medicinesList: MutableList<Medicine>  = mutableListOf()
    val db = FirebaseFirestore.getInstance()

    lateinit var listview: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)

        listview = findViewById(R.id.medicineDetails)

    }


    private fun GetMedicineDetails() {

//        medicinesList.clear()
//
//        db.collection("medicines")
//                .whereEqualTo("medicine_id", "8CIPF4KpNNc6mxaNs2Xx")
//                .get()
//                .addOnCompleteListener {
//                    if(it.isSuccessful){
//                        for (med in it.result!!){
//                            val medicine = med.toObject(Medicine::class.java)
//                            medicinesList.add(medicine)
//                        }
//                        val adapter = MedicineDetailsAdapter(applicationContext, R.layout.activity_medicine_details, medicinesList)
//                        listview.adapter = adapter
//                    }
//                }.addOnFailureListener{
//                    Log.d("Failled", "Failed to get data");
//                }
    }

}
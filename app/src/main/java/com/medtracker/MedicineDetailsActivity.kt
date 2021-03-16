package com.medtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.adapter.MedicineDetailsAdapter
import com.medtracker.adapter.PrescriptionAdapter
import com.medtracker.model.Medicine
import com.medtracker.model.Prescription

class MedicineDetailsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    lateinit var list: ListView

    var detailsList: MutableList<Medicine>  = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)

        list = findViewById(R.id.medicineDetails)

        GetMedicineDetails()


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


    private fun GetMedicineDetails() {

        val medName = intent.getStringExtra("medName")

        Log.d("Ranim" , medName.toString())

        detailsList.clear()

        val db = FirebaseFirestore.getInstance()
        db.collection("medicines")
            .whereEqualTo("name", medName)
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful){
                    for (med in it.result!!){
                        val medicine = med.toObject(Medicine::class.java)
                        Log.d("****", medicine.toString())

                        detailsList.add(medicine)
                    }
                    val adapter = MedicineDetailsAdapter(
                        applicationContext,
                        R.layout.medicinedetails,
                        detailsList
                    )
                    list.adapter = adapter
                }
            }.addOnFailureListener{
                Log.d("Failled", "Failed to get data");
            }
    }

}
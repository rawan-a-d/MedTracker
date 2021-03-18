package com.medtracker.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener
import com.medtracker.utils.PreferenceHelper
import com.medtracker.R
import com.medtracker.adapters.HomeAdapter
import com.medtracker.models.*


class MainActivity : AppCompatActivity() {
    lateinit var list: ListView

    var medicineTimes: MutableList<MedicineTime>  = mutableListOf()

    // db connection, reference
    private val dbPrescriptions = FirebaseFirestore.getInstance().collection(NODE_PRESCRIPTIONS)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.medicineTimes)

        val prefs = PreferenceHelper.customPrefs(this, Shared_Prefs)

        // get current user id
        val id = prefs.getString("currentProfileId", null)

        val picker = findViewById<DayScrollDatePicker>(R.id.date_picker)

        picker.getSelectedDate(OnDateSelectedListener { date ->
            run {

                if (date != null && id != null) {
                    // pass selected date as Timestamp
                    fetchMedicineTimes(id, Timestamp(date))
                }
            }
        })


        // pass selected date as Timestamp
        fetchMedicineTimes(id!!, Timestamp.now())



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
                R.id.switchProfiles -> {
                    val intent = Intent(this, ChooseProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
            }
            true
        }
    }


    private fun fetchMedicineTimes(id: String, date: Timestamp) {
        // get id of current user from share preferences
        dbPrescriptions
            .whereEqualTo(FieldPath.of("user_id"), id)
            .whereEqualTo("is_done", false)
            .whereGreaterThanOrEqualTo(FieldPath.of("end_date"), date)// where end_date >= date
            // SELECT * FROM prescriptions WHERE user_id = id AND is_done = false AND (start_date <= date AND end_date >= date)
            .get()
            .addOnSuccessListener { snapshot ->
                val prescriptions = mutableListOf<Prescription>()

                for (prescriptionSnapshot in snapshot) {
                    prescriptions.add(prescriptionSnapshot.toObject(Prescription::class.java))
                }

                // format the prescriptions to create MedicineTime
                formatPrescriptions(prescriptions, date)
            }
            .addOnFailureListener {
                Log.d("Prescriptions", "We were not able to get prescriptions")
            }
    }



    // prescription
    private fun formatPrescriptions(prescriptions: List<Prescription>, date: Timestamp) {
        medicineTimes.clear()

       // val startDate = "1/4/2021"
       // val sdf = SimpleDateFormat("dd/MM/yyyy")
       // val strDate = sdf.parse(startDate)

        // loop over time
        for(time in 1..24) { // 1 AM to 24 PM
            // loop over prescriptions
            for (prescription in prescriptions){ // runs 24 * number of prescriptions
                val times = prescription.times


                if (times != null) {
                    // if times of medicine contains variable time
                    if(times.contains(time) && (prescription.start_date)?.toDate()?.before(date.toDate()) == true) {

                        var medicineTime = MedicineTime(
                            time,
                            prescription.dose,
                            prescription.medicine
                        ) // create new MedicineTime

                        medicineTimes.add(medicineTime)

                    }
                }

            }

            val adapter = HomeAdapter(
                applicationContext,
                R.layout.activity_main_details,
                medicineTimes
            )
            list.adapter = adapter
        }
    }

}
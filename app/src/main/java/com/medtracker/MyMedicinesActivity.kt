package com.medtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.medtracker.model.Medicine

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

    lateinit var listview: ListView

    lateinit var ref: DatabaseReference
     var medicinesList: MutableList<String>  = mutableListOf()

    lateinit var  textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_medicines)

        GetMeStuff()
        ref = FirebaseDatabase.getInstance().getReference("medicines")

        listview = findViewById(R.id.medicinesList)
        textView = findViewById(R.id.testy)

        ref.addValueEventListener(object: ValueEventListener{
             var Ranim: String = "test"
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d("RanimSnap", "in medicinres list")
                if (snapshot.exists()){
                    Log.d("RanimSnap", "in medicinres list")
                    for (med in snapshot.children){
                        val medicine = med.getValue(Medicine::class.java)
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


        /* Bottom nav bar*/
        //intialize and assign functionalities
        val bottomNavigator = findViewById<BottomNavigationView>(R.id.bottom_navigator)

        //set the selected icon on mymedicines page
//        bottomNavigator.setSelectedItemId(R.id.MyMedicines)
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

    private fun GetMeStuff() {
        val db = FirebaseFirestore.getInstance()
        db.collection("medicines")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                Log.d("hi","in coplete listner")
                if(it.isSuccessful){
                    Log.d("hi","in sucess complete listner")

                    for (med in it.result!!){
                        result.append(med.data.getValue("name")).append(" ")
//                        val medicine = med.getValue(medicine::class.java)
//                        medicinesList.add(result.toString())
                    }
                     textView.text = result
//                    val adapter = adapter(applicationContext, R.layout.medicines, medicinesList)
//                    listview.adapter = adapter

                }
            }.addOnFailureListener{
                Log.d("hi", "hi2");
            }
        val medicine: MutableMap<String, Any> = HashMap()
        medicine[""]

    }

//    override fun onStart() {
//        super.onStart()
//        adapter?.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter?.stopListening()
//    }

}


//private class MedicinesViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
////    internal fun setMedicineName(medName: String) {
////        val textView = view.findViewById<TextView>(R.id.medName)
////        textView.text = medName
////    }
//
//    var medName = view.findViewById<TextView>(R.id.medName)
//
//    public fun ViewHolder(@NonNull itemView: View){
//        super.itemView
//        medName = itemView.findViewById(R.id.medName)
//
//    }
//}
//
//private class MedicineFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<medicineModel>) : FirestoreRecyclerAdapter<medicineModel, MedicinesViewHolder>(options) {
//
//    @NonNull
//    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MedicinesViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_medicine_item, parent, false)
//        return MedicinesViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MedicinesViewHolder, position: Int, model: medicineModel) {
//        holder.medName.setText(model.getName())
//    }
//}
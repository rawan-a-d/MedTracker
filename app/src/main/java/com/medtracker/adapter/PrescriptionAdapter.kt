package com.medtracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.medtracker.R
import com.medtracker.model.Prescription

class PrescriptionAdapter(context: Context, val layoutResId: Int, val medList: List<Prescription>):
    ArrayAdapter<Prescription>(context,layoutResId,medList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val medName = view.findViewById<TextView>(R.id.medName)
        val info = view.findViewById<TextView>(R.id.dose)
        val divider = view.findViewById<TextView>(R.id.divider)
        val qrBtn = view.findViewById<Button>(R.id.qrButton)

        val medicines = medList[position]

        qrBtn.setOnClickListener {
            Toast.makeText(this.context, "Med id is: " + medicines.medicine?.id, Toast.LENGTH_LONG).show()

        }

        medName.setOnClickListener {
            Toast.makeText(this.context, "You clicked on " + medicines.medicine?.id, Toast.LENGTH_LONG).show()
        }

        medName.text = medicines.medicine?.name
        info.text = medicines.dose.toString() + " pill(s)  " + medicines.frequency.toString() + " times per day"
        divider.text = "_____________________________________________________"

        return view
    }
}

//private fun String.geName() {
//
//}

package com.medtracker.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.medtracker.R
import com.medtracker.model.MedicineTime


class HomeAdapter (context: Context, val layoutResId: Int, val medicineTimes: List<MedicineTime>):
        ArrayAdapter<MedicineTime>(context,layoutResId,medicineTimes) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        val view: View = layoutInflater.inflate(layoutResId, null)

        val time = view.findViewById<TextView>(R.id.text_view_prescription_time)
        val name = view.findViewById<TextView>(R.id.text_view_prescription)
        val dose = view.findViewById<TextView>(R.id.text_view_prescription_dose)

        val medicineTime = medicineTimes[position]

        // Display data
        time.text = medicineTime.time?.toString()
        name.text = medicineTime.medicine?.name
        dose.text = medicineTime.dose?.toString() + " pill(s)"

        return view
    }
}

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
import com.medtracker.model.Medicine
import com.medtracker.model.Prescription

class MedicineDetailsAdapter (context: Context, val layoutResId: Int, val medList: List<Medicine>):
    ArrayAdapter<Medicine>(context,layoutResId,medList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        val view: View = layoutInflater.inflate(layoutResId, null)

        val desc = view.findViewById<TextView>(R.id.reasonToUse)
        val usage = view.findViewById<TextView>(R.id.HowToUse)
        val effects = view.findViewById<TextView>(R.id.sideEffect)


        val medicines = medList[position]

        desc.text = medicines.description
        usage.text = medicines.usage
        effects.text = medicines.side_effects

        return view
    }
}
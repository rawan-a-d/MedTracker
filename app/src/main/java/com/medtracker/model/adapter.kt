package com.medtracker.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.medtracker.R

class adapter(context: Context, val layoutResId: Int, val medList: List<String>):
    ArrayAdapter<String>(context,layoutResId,medList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val medName = view.findViewById<TextView>(R.id.medName)

        val medicines = medList[position]

//        medName.text = medicines.name

        return view
    }
}
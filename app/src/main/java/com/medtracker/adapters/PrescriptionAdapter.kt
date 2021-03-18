package com.medtracker.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.medtracker.activities.MedicineDetailsActivity
import com.medtracker.R
import com.medtracker.handlers.QRHandler
import com.medtracker.models.Prescription


class PrescriptionAdapter(context: Context, val layoutResId: Int, val medList: List<Prescription>):
    ArrayAdapter<Prescription>(context,layoutResId,medList) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val medName = view.findViewById<TextView>(R.id.medName)
        val info = view.findViewById<TextView>(R.id.dose)
        val divider = view.findViewById<TextView>(R.id.divider)
        val qrBtn = view.findViewById<Button>(R.id.qrButton)

        var context:Context


        val medicines = medList[position]

        qrBtn.setOnClickListener {

            // create a new view
            context = parent.context
            val alertbox = AlertDialog.Builder(view.getRootView().getContext())
            val itemLayoutView: View = LayoutInflater.from(context).inflate(
                    R.layout.qr_dialog, null)

            var qr = itemLayoutView.findViewById<View>(R.id.qrImageView) as ImageView


            val medicineName = medicines.medicine?.name
            val medicineId = medicines.medicine?.id

            qr.setImageBitmap(
                    QRHandler.StringToQRCode(
                            context,
                            "Medicine name: " + medicineName + " with id: " + medicineId
                    )
            )

            alertbox.setView(itemLayoutView)

            alertbox.show()

        }

        medName.setOnClickListener {

            val intent = Intent(view.getRootView().getContext(), MedicineDetailsActivity::class.java)
            intent.putExtra("medName", medicines.medicine?.name)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(intent);

        }

        medName.text = medicines.medicine?.name
        info.text = medicines.dose.toString() + " pill(s)  " + medicines.frequency.toString() + " times per day"
        divider.text = "_____________________________________________________"

        return view
    }
}


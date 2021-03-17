package com.medtracker.model

//dose
// medicines (id, name)
class MedicineUsage(
    val medicine: Medicine? = null,
    val dose: Int? = null) {


    override fun toString(): String {
        return "MedicineUsage(medicine=$medicine, dose=$dose)"
    }
}
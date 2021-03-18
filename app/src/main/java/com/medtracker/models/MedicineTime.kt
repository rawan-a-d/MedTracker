package com.medtracker.models

data class MedicineTime(
    var time: Int? = null,
    var dose : Int? = null,
    var medicine: Medicine? = null
){
    // Methods
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MedicineTime

        if (time != other.time) return false
        if (dose != other.dose) return false
        if (medicine != other.medicine) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time ?: 0
        result = 31 * result + (dose ?: 0)
        result = 31 * result + (medicine?.hashCode() ?: 0)
        return result
    }


}


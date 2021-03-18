package com.medtracker.models

import com.google.firebase.Timestamp

data class Prescription (
    var id: String? = null,
    val user_id: String? = null,
    val medicine: Medicine? = null,
    val dose: Int? = null,
    val frequency: Int? = null,
    val is_done: Boolean? = null,
    val times: List<Int>? = null,
    val start_date: Timestamp? = null
){
    // Methods
    override fun equals(other: Any?): Boolean {
        return if (other is Prescription) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (medicine?.name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Prescription(id=$id, user_id=$user_id, medicine=$medicine, dose=$dose, frequency=$frequency, is_done=$is_done, times=$times)"
    }
}

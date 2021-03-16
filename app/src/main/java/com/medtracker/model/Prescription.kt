package com.medtracker.model

data class Prescription (
        var id: String? = null,
        val user_id: String? = null,
        val medicine: Medicine? = null,
        val dose: Int? = null,
        val frequency: Int? = null,
        val is_done: Boolean? = null,
        val times: List<Int>? = null
){

    override fun toString(): String {
        return "Prescription(id=$id, user_id=$user_id, medicine=$medicine, dose=$dose, frequency=$frequency, is_done=$is_done, times=$times)"
    }
}
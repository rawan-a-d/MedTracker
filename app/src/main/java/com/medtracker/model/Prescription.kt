package com.medtracker.model

import com.google.firebase.Timestamp
import java.util.*

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
}

package com.medtracker.model

class Prescription (
        val medicine_id: String,
        val user_id: String,
        val dose: Int,
        val frequency: Int,
        val is_done: Boolean,
){
    constructor(): this("","",0, 0, false) {

    }

}
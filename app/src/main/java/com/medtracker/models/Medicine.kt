package com.medtracker.models

data class Medicine (
    val id: String,
    val name: String,
    val description: String,
    val usage: String,
    val side_effects: String,
) {
    constructor() : this("", "", "", "", "") {

    }

    override fun toString(): String {
        return "Medicine(id='$id', name='$name', description='$description', usage='$usage', side_effects='$side_effects')"
    }
}
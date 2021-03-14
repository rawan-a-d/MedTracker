package com.medtracker.model

class medicine (
    val id: String,
    val name: String,
){
    constructor(): this("", "") {

    }

}

//class medicine {
//
//    private var name: String = ""
//
//    private var description: String? = null
//    private var side_effects: String? = null
//    private var allergies: String? = null
//    private var usage: String? = null
//        get() = field
//
//    private val varcompany: String? = null
//
//    constructor(name: String, usage: String?) {
//        this.name = name
//        this.usage = usage
//    }
//
//    public fun getName(): String{
//        return this.name
//    }
//
//
//}
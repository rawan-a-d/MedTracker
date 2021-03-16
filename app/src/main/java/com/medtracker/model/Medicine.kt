package com.medtracker.model

class Medicine (
    val id: String,
    val name: String,
    val description: String,
    val usage: String,
    val side_effects: String,
){
    constructor(): this("", "","","","") {

    }

    override fun toString(): String {
        return "Medicine(id='$id', name='$name', description='$description', usage='$usage', side_effects='$side_effects')"
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
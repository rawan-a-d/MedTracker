package com.medtracker.model

data class User(
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var phone_number: String? = null,
    var type: String? = null,
    var children: List<User>? = null) {

    // Methods
    override fun equals(other: Any?): Boolean {
        return if (other is User) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (firstname?.hashCode() ?: 0)
        return result
    }
}
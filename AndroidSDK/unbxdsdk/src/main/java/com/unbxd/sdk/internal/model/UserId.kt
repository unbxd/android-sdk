package com.unbxd.sdk.internal.model
import java.util.*

class UserId {

    val id: String
    var visitType: String
    internal var expiry: Date

    internal constructor(id: String, visitType: String, expiry: Date) {
        this.id = id
        this.visitType = visitType
        this.expiry = expiry
    }

    internal fun hasExpired(): Boolean {
        val currentDate = Date()
        return currentDate >= this.expiry
    }

    internal fun updateVisitype() {
        this.visitType = "repeat"
        val futureDate = DateUtil.getDateByAddingYears(30)
        this.expiry = futureDate
    }
}
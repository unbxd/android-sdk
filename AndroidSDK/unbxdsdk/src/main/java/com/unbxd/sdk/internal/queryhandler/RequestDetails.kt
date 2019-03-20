package com.unbxd.sdk.internal.queryhandler

interface IRequestDetails {
    val urlString: String
    val auth: Boolean
}

final class RequestDetails(override val urlString: String, override val auth: Boolean) : IRequestDetails {

}
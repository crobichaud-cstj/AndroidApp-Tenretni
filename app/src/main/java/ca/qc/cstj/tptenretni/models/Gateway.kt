package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Gateway(
    val href : String,
    val serialNumber: String,
    val revision : String,
    val pin : String,
    val hash : String,
    val customer : Customer
)

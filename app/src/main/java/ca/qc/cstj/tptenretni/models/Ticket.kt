package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val href : String,
    val ticketNumber : String,
    val createDate : String,
    val priority : String,
    val status : String,
    val customer : Customer,
    val config : Config,
    val connection : Connection
)

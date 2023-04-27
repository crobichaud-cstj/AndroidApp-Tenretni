package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val href : String="",
    val ticketNumber : String="",
    val createdDate : String="",
    val priority : String="",
    val status : String="",
    val customer : Customer = Customer(),
    val config : Config = Config(),
    val connection : Connection = Connection()
)

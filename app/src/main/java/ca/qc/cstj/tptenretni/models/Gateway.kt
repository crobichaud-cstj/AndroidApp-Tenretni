package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Gateway(
    val connection: Connection = Connection(),
    val config: Config= Config(),
    val serialNumber: String="",
    val revision : String="",
    val pin : String="",
    val hash : String="",
    val customer : Customer = Customer(),
    val href : String="",
)

package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val status : String="",
    val download : Float=0F,
    val ip : String="",
    val ping : Int=0,
    val signal : Int=0,
    val upload : Float=0F,
)

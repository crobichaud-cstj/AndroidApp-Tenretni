package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkNode(
    val name : String="",
    val connection : Connection = Connection()
)

package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val latitude : Float=0F,
    val longitude : Float=0F
)

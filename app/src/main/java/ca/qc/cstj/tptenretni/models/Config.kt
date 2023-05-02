package ca.qc.cstj.tptenretni.models

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val kernel : List<String> = listOf(),
    val mac : String="",
    val SSID : String="",
    val version : String="",
    val kernelRevision : String="",
    val installDate : String="",
)

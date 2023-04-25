package ca.qc.cstj.tptenretni.data.datasources

import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.core.JsonDataSource
import ca.qc.cstj.tptenretni.models.Gateway
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString

class GatewayDataSource: JsonDataSource() {
    fun retrieveAll(): List<Gateway> {
        val (_, _, result) = Constants.BaseURL.GATEWAYS.httpGet().responseJson()

        return when (result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}
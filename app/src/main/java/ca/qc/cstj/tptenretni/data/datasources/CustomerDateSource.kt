package ca.qc.cstj.tptenretni.data.datasources

import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.core.JsonDataSource
import ca.qc.cstj.tptenretni.models.Customer
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.models.Ticket
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class CustomerDateSource : JsonDataSource() {
    fun retrieveOne(href: String): Customer {
        val (_, _, result) = href.httpGet().responseJson()

        return when (result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun addGateway(href: String, gateway: Gateway): Gateway {
        //Mettre en json
        val body = json.encodeToString(gateway)
        //Envoie au serveur avec un post
        val (_,_,result) = "$href/gateways".httpPost().jsonBody(body).responseJson()
        //Gérer la réponse
        return when(result) {
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}
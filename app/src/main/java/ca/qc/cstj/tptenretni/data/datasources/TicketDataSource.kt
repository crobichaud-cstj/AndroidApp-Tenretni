package ca.qc.cstj.tptenretni.data.datasources

import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.core.JsonDataSource
import ca.qc.cstj.tptenretni.models.Ticket
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result

class TicketDataSource {

    fun retrieveAll() : List<Ticket>{
        val(_, _, result) = Constants.BaseURL.TICKETS.httpGet().responseJson()

        return when(result){
            is Result.Success -> TODO()
            is Result.Failure -> TODO()
        }
    }
}
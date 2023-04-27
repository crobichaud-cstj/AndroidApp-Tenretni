package ca.qc.cstj.tptenretni.data.datasources

import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.core.JsonDataSource
import ca.qc.cstj.tptenretni.models.Ticket
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString

class TicketDataSource : JsonDataSource() {

    fun retrieveAll() : List<Ticket>{
        val(_, _, result) = Constants.BaseURL.TICKETS.httpGet().responseJson()

        return when(result){
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
    fun retrieveOne(href:String) : Ticket{
        val(_, _, result) = href.httpGet().responseJson()

        return when(result){
            is Result.Success -> json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
    //fun change(href: String, action : String) : Ticket{
      //  val(_, _, result) =  href.httpPost().responseJson()

        //return when (result){
          //  is Result.Success -> json.decodeFromString(result.value.content)
            //is Result.Failure -> throw result.error.exception
        //}
    //}

}
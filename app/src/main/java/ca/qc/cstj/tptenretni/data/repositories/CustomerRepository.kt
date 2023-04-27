package ca.qc.cstj.tptenretni.data.repositories

import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.data.datasources.CustomerDateSource
import ca.qc.cstj.tptenretni.data.datasources.TicketDataSource
import ca.qc.cstj.tptenretni.models.Customer
import ca.qc.cstj.tptenretni.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CustomerRepository {
    private val customerRepository = CustomerDateSource()

    fun retrieveOne(href:String) : Flow<ApiResult<Customer>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(customerRepository.retrieveOne(href)))
            } catch (ex: Exception){
                emit(ApiResult.Error(ex))
            }
            delay(Constants.Refresh_Delay.TICKET_DETAIL_REFRESH)

        }.flowOn(Dispatchers.IO)
    }
}
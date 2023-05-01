package ca.qc.cstj.tptenretni.data.repositories

import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.data.datasources.TicketDataSource
import ca.qc.cstj.tptenretni.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TicketRepository {
    private val ticketRepository = TicketDataSource()
    fun retrieveAll() : Flow<ApiResult<List<Ticket>>> {
        return flow {
            while (true) {

            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(ticketRepository.retrieveAll()))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
            delay(Constants.Refresh_Delay.TICKET_DETAIL_REFRESH)
        }

        }.flowOn(Dispatchers.IO)
    }
    fun retrieveOne(href:String) : Flow<ApiResult<Ticket>> {
        return flow {
            while(true) {
                emit(ApiResult.Loading)
                try {
                    emit(ApiResult.Success(ticketRepository.retrieveOne(href)))
                } catch (ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.Refresh_Delay.TICKET_DETAIL_REFRESH)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun changeStatus(href:String, action:String) : Flow<ApiResult<Ticket>> {
        return flow {
                emit(ApiResult.Loading)
                try {
                    emit(ApiResult.Success(ticketRepository.changeStatus(href, action)))
                } catch (ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
        }.flowOn(Dispatchers.IO)
    }

}
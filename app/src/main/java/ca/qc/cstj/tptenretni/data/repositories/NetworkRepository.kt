package ca.qc.cstj.tptenretni.data.repositories

import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.data.datasources.NetworkDataSource
import ca.qc.cstj.tptenretni.data.datasources.TicketDataSource
import ca.qc.cstj.tptenretni.models.Network
import ca.qc.cstj.tptenretni.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkRepository {
    private val networkDataSource = NetworkDataSource()
    fun retrieve() : Flow<ApiResult<Network>> {
        return flow {
            while (true) {

                emit(ApiResult.Loading)
                try {
                    emit(ApiResult.Success(networkDataSource.retrieve()))
                } catch (ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.Refresh_Delay.NETWORK_REFRESH)
            }

        }.flowOn(Dispatchers.IO)
    }
}
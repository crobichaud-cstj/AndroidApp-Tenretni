package ca.qc.cstj.tptenretni.data.repositories

import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.data.datasources.GatewayDataSource
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GatewayRepository {

    private val gatewayRepository = GatewayDataSource()

    fun retrieveAll() : Flow<ApiResult<List<Gateway>>> {
        return flow {
            while(true) {
                emit(ApiResult.Loading)
                try {
                    emit(ApiResult.Success(gatewayRepository.retrieveAll()))
                } catch (ex:Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.Refresh_Delay.GATEWAY_REFRESH)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun retrieveOne(href : String) : Flow<ApiResult<Gateway>> {
        return flow {
            while(true) {
                emit(ApiResult.Loading)
                try {
                    emit(ApiResult.Success(gatewayRepository.retrieveOne(href)))
                } catch (ex:Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.Refresh_Delay.GATEWAY_REFRESH)
            }
        }.flowOn(Dispatchers.IO)
    }
}
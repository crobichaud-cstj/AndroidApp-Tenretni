package ca.qc.cstj.tptenretni.ui.gateways.detail

import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.ui.gateways.GatewaysUiState
import java.lang.Exception

sealed class DetailGatewayUiState {
    object Loading: DetailGatewayUiState()
    class Success(val gateway: Gateway): DetailGatewayUiState()
    class Error(val exception: Exception) : DetailGatewayUiState()
}
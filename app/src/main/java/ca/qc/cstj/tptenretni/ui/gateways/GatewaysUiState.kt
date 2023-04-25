package ca.qc.cstj.tptenretni.ui.gateways

import ca.qc.cstj.tptenretni.models.Gateway
import java.lang.Exception

sealed class GatewaysUiState {
    object Loading: GatewaysUiState()
    class Success(val Gateways:List<Gateway>): GatewaysUiState()
    class Error(val exception: Exception) : GatewaysUiState()


}
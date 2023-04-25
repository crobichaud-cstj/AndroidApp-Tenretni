package ca.qc.cstj.tptenretni.ui.gateways

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.data.repositories.GatewayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GatewaysViewModel : ViewModel() {

    private val gatewayRepository = GatewayRepository()

    private val _gatewaysUiState = MutableStateFlow<GatewaysUiState>(GatewaysUiState.Loading)
    val gatewaysUiState = _gatewaysUiState.asStateFlow()
    init {
        refreshPlanets()
    }

    fun refreshPlanets() {
        viewModelScope.launch {
            gatewayRepository.retrieveAll().collect {
                _gatewaysUiState.update { _ ->
                    when (it) {
                        is ApiResult.Error -> GatewaysUiState.Error(it.exception)
                        ApiResult.Loading -> GatewaysUiState.Loading
                        is ApiResult.Success -> GatewaysUiState.Success(it.data)
                    }
                }
            }
        }
    }
}
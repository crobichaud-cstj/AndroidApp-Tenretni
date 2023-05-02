package ca.qc.cstj.tptenretni.ui.gateways.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.data.repositories.GatewayRepository
import ca.qc.cstj.tptenretni.ui.gateways.GatewaysUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailGatewayViewModel(private val href: String) : ViewModel() {

    private val gatewayRepository = GatewayRepository()

    private val _detailGatewaysUiState = MutableStateFlow<DetailGatewayUiState>(DetailGatewayUiState.Loading)
    val detailGatewaysUiState = _detailGatewaysUiState.asStateFlow()
    init {
        refreshGateways()
    }

    private fun refreshGateways() {
        viewModelScope.launch {
            gatewayRepository.retrieveOne(href).collect {
                _detailGatewaysUiState.update { _ ->
                    when (it) {
                        is ApiResult.Error -> DetailGatewayUiState.Error(it.exception)
                        ApiResult.Loading -> DetailGatewayUiState.Loading
                        is ApiResult.Success -> DetailGatewayUiState.Success(it.data)
                    }
                }
            }
        }
    }


    class Factory(private val href: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }
    }

}
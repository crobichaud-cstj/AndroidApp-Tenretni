package ca.qc.cstj.tptenretni.ui.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.data.repositories.NetworkRepository
import ca.qc.cstj.tptenretni.data.repositories.TicketRepository
import ca.qc.cstj.tptenretni.ui.tickets.TicketsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NetworkViewModel : ViewModel() {

    private val networkRepository = NetworkRepository()

    private val _networkUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Loading)
    val networkUiState = _networkUiState.asStateFlow()

    init {
        loadNetwork()
    }

    fun loadNetwork(){
        viewModelScope.launch {
            networkRepository.retrieve().collect { apiResult ->
                _networkUiState.update { _ ->
                    when(apiResult) {
                        is ApiResult.Error -> NetworkUiState.Error(apiResult.exception)
                        ApiResult.Loading -> NetworkUiState.Loading
                        is ApiResult.Success -> NetworkUiState.Success(apiResult.data)
                    }

                }
            }
        }
    }
}
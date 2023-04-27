package ca.qc.cstj.tptenretni.ui.tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.data.repositories.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TicketsViewModel : ViewModel() {

    private val ticketsRepository = TicketRepository()

    private val _ticketsUiState = MutableStateFlow<TicketsUiState>(TicketsUiState.Loading)
    val ticketsUiState = _ticketsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            ticketsRepository.retrieveAll().collect { apiResult ->
                _ticketsUiState.update { _ ->
                    when(apiResult) {
                        is ApiResult.Error -> TicketsUiState.Error(apiResult.exception)
                        ApiResult.Loading -> TicketsUiState.Loading
                        is ApiResult.Success -> TicketsUiState.Success(apiResult.data)
                    }

                }
            }
        }
    }
}
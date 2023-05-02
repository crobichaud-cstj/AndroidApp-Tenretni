package ca.qc.cstj.tptenretni.ui.tickets.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tptenretni.core.ApiResult
import ca.qc.cstj.tptenretni.data.repositories.CustomerRepository
import ca.qc.cstj.tptenretni.data.repositories.TicketRepository
import ca.qc.cstj.tptenretni.data.repositories.GatewayRepository
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.models.Ticket
import ca.qc.cstj.tptenretni.ui.gateways.GatewaysUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailTicketViewModel(private val href: String) : ViewModel() {
    private val ticketRepository = TicketRepository()
    private val customerRepository = CustomerRepository()
    private val gatewayRepository = GatewayRepository()

    private val _detailTicketUiState =
        MutableStateFlow<DetailTicketUiState>(DetailTicketUiState.Loading)
    val detailTicketUiState = _detailTicketUiState.asStateFlow()

    init {
        viewModelScope.launch {
            ticketRepository.retrieveOne(href).collect { apiResult ->
                _detailTicketUiState.update {
                    when (apiResult) {
                        is ApiResult.Error -> DetailTicketUiState.Error(apiResult.exception)
                        ApiResult.Loading -> DetailTicketUiState.Loading
                        is ApiResult.Success -> {
                            loadCustomer(apiResult.data.customer.href)
                            DetailTicketUiState.SuccessTicket(apiResult.data)

                        }
                    }
                }
            }
        }
    }

    fun changeStatus(action:String){
        viewModelScope.launch {
            ticketRepository.changeStatus(href,action).collect { apiResult ->
                _detailTicketUiState.update {
                    when (apiResult) {
                        is ApiResult.Error -> DetailTicketUiState.Error(apiResult.exception)
                        ApiResult.Loading -> DetailTicketUiState.Loading
                        is ApiResult.Success -> {
                            DetailTicketUiState.SuccessTicket(apiResult.data);
                        }
                    }
                }
            }
        }
    }

    private fun loadCustomer(href: String) {
        viewModelScope.launch {
            customerRepository.retrieveOne(href).collect { apiResult ->
                _detailTicketUiState.update {
                    when (apiResult) {
                        is ApiResult.Error -> DetailTicketUiState.Error(apiResult.exception)
                        ApiResult.Loading -> DetailTicketUiState.Loading
                        is ApiResult.Success -> {
                            DetailTicketUiState.SuccessCustomer(apiResult.data);
                        }
                    }
                }
            }
        }
    }

    fun addGateway(ticket: Ticket, gateway:Gateway) {
        viewModelScope.launch {
            customerRepository.addGateway(ticket, gateway).collect{apiResult ->
                _detailTicketUiState.update {
                    when(apiResult){
                        is ApiResult.Error -> DetailTicketUiState.Error(apiResult.exception)
                        ApiResult.Loading -> DetailTicketUiState.Loading
                        is ApiResult.Success -> DetailTicketUiState.SuccessGateway(apiResult.data)
                    }
                }
            }
        }
    }

    private fun refreshGateways() {
        viewModelScope.launch {
            gatewayRepository.retrieveAll().collect {
                _detailTicketUiState.update { _ ->
                    when (it) {
                        is ApiResult.Error -> DetailTicketUiState.Error(it.exception)
                        ApiResult.Loading -> DetailTicketUiState.Loading
                        is ApiResult.Success -> DetailTicketUiState.SuccessGateways(it.data)
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

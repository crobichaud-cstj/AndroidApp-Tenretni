package ca.qc.cstj.tptenretni.ui.tickets.detail

import ca.qc.cstj.tptenretni.models.Customer
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.models.Ticket

sealed class DetailTicketUiState {
    class Error(val exception: Exception) : DetailTicketUiState()
    class SuccessTicket(val ticket: Ticket) : DetailTicketUiState()
    class SuccessCustomer(val customer: Customer) : DetailTicketUiState()
    class SuccessGateway(val gateway: Gateway) : DetailTicketUiState()
    class SuccessGateways(val Gateways:List<Gateway>) : DetailTicketUiState()
    object Loading: DetailTicketUiState()
}
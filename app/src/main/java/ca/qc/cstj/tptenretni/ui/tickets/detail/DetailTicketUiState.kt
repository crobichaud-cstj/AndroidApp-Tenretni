package ca.qc.cstj.tptenretni.ui.tickets.detail

import ca.qc.cstj.tptenretni.models.Customer
import ca.qc.cstj.tptenretni.models.Ticket

sealed class DetailTicketUiState {
    class Error(val exception: Exception) : DetailTicketUiState()
    class SuccessTicket(val ticket: Ticket) : DetailTicketUiState()
    class SuccessCustomer(val customer: Customer) : DetailTicketUiState()
    object Loading: DetailTicketUiState()
}
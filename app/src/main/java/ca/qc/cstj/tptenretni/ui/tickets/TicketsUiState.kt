package ca.qc.cstj.tptenretni.ui.tickets

import ca.qc.cstj.tptenretni.models.Ticket

sealed class TicketsUiState {
    object Loading : TicketsUiState()
    class Error(val exception: Exception) : TicketsUiState()
    class Success(val ticket: List<Ticket>): TicketsUiState()
}
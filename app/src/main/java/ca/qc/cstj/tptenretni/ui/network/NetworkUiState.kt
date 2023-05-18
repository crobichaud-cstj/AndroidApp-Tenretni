package ca.qc.cstj.tptenretni.ui.network

import ca.qc.cstj.tptenretni.models.Network
import ca.qc.cstj.tptenretni.models.NetworkNode
import ca.qc.cstj.tptenretni.models.Ticket
import ca.qc.cstj.tptenretni.ui.tickets.TicketsUiState

sealed class NetworkUiState {
    object Loading : NetworkUiState()
    class Error(val exception: Exception) : NetworkUiState()
    class Success(val network: Network): NetworkUiState()
}
package ca.qc.cstj.tptenretni.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding
import ca.qc.cstj.tptenretni.databinding.FragmentTicketsBinding
import ca.qc.cstj.tptenretni.models.Ticket
import ca.qc.cstj.tptenretni.ui.adapters.TicketRecycleViewAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val binding: FragmentTicketsBinding by viewBinding()
    private val viewModel: TicketsViewModel by activityViewModels()

    private val ticketRecyclerViewAdapter = TicketRecycleViewAdapter(listOf(), ::onTicketClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvTickets.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ticketRecyclerViewAdapter
        }

        viewModel.ticketsUiState.onEach {
            when(it){
                is TicketsUiState.Error -> {
                    Toast.makeText(requireContext(), it.exception?.localizedMessage ?: getString(R.string.apiErrorMessage), Toast.LENGTH_SHORT).show()
                }
                TicketsUiState.Loading -> {
                    binding.rcvTickets.visibility = View.GONE
                    binding.pgbLoading.show()
                }
                is TicketsUiState.Success -> {
                    binding.rcvTickets.visibility = View.VISIBLE

                    ticketRecyclerViewAdapter.tickets = it.ticket
                    ticketRecyclerViewAdapter.notifyDataSetChanged()
                    binding.pgbLoading.hide()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun onTicketClick(ticket: Ticket){

    }
}
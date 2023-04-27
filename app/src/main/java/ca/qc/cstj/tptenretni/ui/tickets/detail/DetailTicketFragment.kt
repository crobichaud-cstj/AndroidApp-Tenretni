package ca.qc.cstj.tptenretni.ui.tickets.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.databinding.FragmentDetailTicketBinding
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding
import ca.qc.cstj.tptenretni.ui.gateways.GatewaysViewModel
import ca.qc.cstj.tptenretni.ui.tickets.TicketsFragmentArgs
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket) {
    private val args: TicketsFragmentArgs by navArgs()

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels{
        DetailTicketViewModel.Factory(args.href)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detailTicketUiState.onEach {
            when(it){
                is DetailTicketUiState.Error -> {
                    Toast.makeText(requireContext(), it.exception.localizedMessage, Toast.LENGTH_LONG).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }
                DetailTicketUiState.Loading -> Unit
                is DetailTicketUiState.SuccessTicket -> {
                    binding.txvHref.text=it.ticket.href
                }

                is DetailTicketUiState.SuccessCustomer -> Unit
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
}
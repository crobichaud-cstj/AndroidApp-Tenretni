package ca.qc.cstj.tptenretni.ui.tickets.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.ColorHelper
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.core.DateHelper
import ca.qc.cstj.tptenretni.databinding.FragmentDetailTicketBinding
import ca.qc.cstj.tptenretni.models.Customer
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.models.Ticket
import ca.qc.cstj.tptenretni.ui.tickets.TicketsFragmentArgs
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ca.qc.cstj.tptenretni.ui.adapters.GatewayRecyclerViewAdapter
import ca.qc.cstj.tptenretni.ui.gateways.GatewaysFragmentDirections

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket) {
    private val args: TicketsFragmentArgs by navArgs()

    private var position : LatLng? = null
    private var customer : Customer = Customer()
    private var ticket: Ticket = Ticket()
    private lateinit var customerName : String

    private lateinit var gatewayRecyclerViewAdapter: GatewayRecyclerViewAdapter


    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels {
        DetailTicketViewModel.Factory(args.href)

    }

    private val scanQRCode = registerForActivityResult(ScanQRCode(), ::handleQuickieResult)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnInstall.setOnClickListener{
            scanQRCode.launch(null);
        }

        gatewayRecyclerViewAdapter = GatewayRecyclerViewAdapter(listOf(),::onGatewayClick)

        binding.rcvGateways.adapter = gatewayRecyclerViewAdapter

        viewModel.detailTicketUiState.onEach {
            when (it) {
                is DetailTicketUiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }

                DetailTicketUiState.Loading -> {

                }
                is DetailTicketUiState.SuccessTicket -> {
                    ticket = it.ticket
                    binding.chpTicketStatus.text = ticket.status
                    binding.chpTicketPriority.text = ticket.priority
                    binding.chpTicketPriority.chipBackgroundColor = ColorHelper.ticketPriorityColor(binding.root.context, ticket.priority)
                    binding.chpTicketStatus.chipBackgroundColor = ColorHelper.ticketStatusColor(binding.root.context, ticket.status)
                    binding.txvTicketDate.text = DateHelper.formatISODate(ticket.createdDate)
                    binding.txvTicketId.text = binding.root.context.getString(R.string.ticket_id, ticket.ticketNumber)

                    when(Constants.TicketStatus.valueOf(ticket.status)){
                        Constants.TicketStatus.Open -> statusOpen()
                        Constants.TicketStatus.Solved -> statusSolve()
                    }

                }
                is DetailTicketUiState.SuccessCustomer -> {
                    customer = it.customer
                    ticket.customer = customer
                    binding.txvNom.text = customer.firstName + " " + customer.lastName
                    binding.txvVille.text = customer.city
                    binding.txvAdresse.text = customer.address
                    customerName = customer.firstName + " " + customer.lastName
                    position = LatLng(customer.coord.latitude.toDouble(), customer.coord.longitude.toDouble())
                }

                is DetailTicketUiState.SuccessGateways -> {
                    binding.rcvGateways.visibility = View.VISIBLE
                    gatewayRecyclerViewAdapter.gateways = it.Gateways
                    gatewayRecyclerViewAdapter.notifyDataSetChanged()
                }

                is DetailTicketUiState.SuccessGateway -> {

                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.btnOpen.setOnClickListener{
            viewModel.changeStatus(Constants.NameStatus.OPEN)
            statusOpen()
        }

        binding.btnSolve.setOnClickListener{
            viewModel.changeStatus(Constants.NameStatus.SOLVE)
            statusSolve()
        }

        binding.fabLocation.setOnClickListener{
            if(position != null) {
                val action = DetailTicketFragmentDirections.actionDetailTicketFragmentToMapsActivity(position!!, customerName)
                findNavController().navigate(action)
            }
        }

        binding.rcvGateways.apply {
            layoutManager = GridLayoutManager(requireContext(),Constants.RecyclerAdapters.Gateways)
            adapter = gatewayRecyclerViewAdapter

        }
    }

    private fun statusOpen(){
        binding.btnOpen.visibility = View.INVISIBLE
        binding.btnInstall.visibility = View.VISIBLE
        binding.btnSolve.visibility = View.VISIBLE
    }

    private fun statusSolve(){
        binding.btnOpen.visibility = View.VISIBLE
        binding.btnInstall.visibility = View.GONE
        binding.btnSolve.visibility = View.GONE
    }

    private fun handleQuickieResult(qrResult: QRResult) {
        when (qrResult) {
            is QRResult.QRSuccess -> {
                val gateway:Gateway = Json.decodeFromString(qrResult.content.rawValue)
                viewModel.addGateway(ticket, gateway )
            }
            QRResult.QRUserCanceled -> Unit
            QRResult.QRMissingPermission -> Unit
            is QRResult.QRError -> Unit
        }
    }

    private fun onGatewayClick(gateway: Gateway){
    }
}
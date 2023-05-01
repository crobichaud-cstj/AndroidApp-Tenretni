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
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.databinding.FragmentDetailTicketBinding
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

class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket) {
    private val args: TicketsFragmentArgs by navArgs()

    private var position : LatLng? = null
    private lateinit var customerName : String

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels {
        DetailTicketViewModel.Factory(args.href)

    }


    private val scanQRCode = registerForActivityResult(ScanQRCode(), ::handleQuickieResult)

    private var ticket: Ticket = Ticket()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInstall.setOnClickListener{
            scanQRCode.launch(null);
        }

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

                DetailTicketUiState.Loading -> Unit
                is DetailTicketUiState.SuccessTicket -> {
                    ticket = it.ticket
                    binding.txvHref.text = it.ticket.href
                    binding.txvHref.text=it.ticket.href

                    when(Constants.TicketStatus.valueOf(it.ticket.status)){
                        Constants.TicketStatus.Open -> statusOpen()
                        Constants.TicketStatus.Solved -> statusSolve()
                    }

                }

                is DetailTicketUiState.SuccessCustomer -> {
                    ticket.customer = it.customer
                    binding.textView2.text = it.customer.href
                    customerName = it.customer.firstName + " " + it.customer.lastName
                    position = LatLng(it.customer.coord.latitude.toDouble(), it.customer.coord.longitude.toDouble())
                }

                is DetailTicketUiState.SuccessGateway -> {
                    binding.txvHref.text = it.gateway.serialNumber
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
    }

    private fun statusOpen(){
        binding.btnOpen.visibility = View.GONE
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
}
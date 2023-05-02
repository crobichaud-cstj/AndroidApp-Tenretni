package ca.qc.cstj.tptenretni.ui.gateways.detail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.ColorHelper.connectionStatusColor
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.databinding.FragmentDetailGatewayBinding
import ca.qc.cstj.tptenretni.databinding.FragmentDetailTicketBinding
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.ui.adapters.GatewayRecyclerViewAdapter
import ca.qc.cstj.tptenretni.ui.gateways.GatewaysViewModel
import ca.qc.cstj.tptenretni.ui.tickets.TicketsFragmentArgs
import ca.qc.cstj.tptenretni.ui.tickets.detail.DetailTicketViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailGatewayFragment: Fragment(R.layout.fragment_detail_gateway) {

    private val args: DetailGatewayFragmentArgs by navArgs()

    private var gateway : Gateway = Gateway()

    private val binding: FragmentDetailGatewayBinding by viewBinding()
    private val viewModel: DetailGatewayViewModel by viewModels {
        DetailGatewayViewModel.Factory(args.href)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detailGatewaysUiState.onEach {
            when(it){
                is DetailGatewayUiState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }
                DetailGatewayUiState.Loading -> Unit
                is DetailGatewayUiState.Success -> {
                    gateway = it.gateway
                    binding.txvSerialNumber.text = gateway.serialNumber
                    binding.txvMac.text = gateway.config.mac
                    binding.txvSSID.text = binding.root.context.getString(R.string.ssid, gateway.config.SSID)
                    binding.txvPIN.text = binding.root.context.getString(R.string.pin, gateway.pin)
                    binding.txvKernelRev.text = binding.root.context.getString(R.string.kernel, gateway.config.kernelRevision)
                    binding.txvVersion.text = binding.root.context.getString(R.string.version, gateway.config.version)
                    binding.txvHash.text = gateway.hash
                    binding.include.txvIp.text = gateway.connection.ip
                    binding.include.txvPing.text = binding.root.context.getString(R.string.ping, gateway.connection.ping)
                    binding.include.txvDownload.text = binding.root.context.getString(R.string.connectionSpeed, gateway.connection.download)
                    binding.include.txvUpload.text = binding.root.context.getString(R.string.connectionSpeed, gateway.connection.upload)
                    binding.include.txvSignal.text = binding.root.context.getString(R.string.signal, gateway.connection.signal)
                    binding.chpStatus.text = gateway.connection.status
                    binding.chpStatus.chipBackgroundColor = connectionStatusColor(binding.root.context, gateway.connection.status)

                    binding.txvHash.text = gateway.hash


                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

}
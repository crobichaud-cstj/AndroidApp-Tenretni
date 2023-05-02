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
import ca.qc.cstj.tptenretni.core.loadFromResource
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
                DetailGatewayUiState.Loading -> {
                    binding.txvIp?.visibility = View.GONE
                    binding.txvPing?.visibility = View.GONE
                    binding.txvDownload?.visibility = View.GONE
                    binding.txvUpload?.visibility = View.GONE
                    binding.txvSignal?.visibility = View.GONE
                    binding.txvOffline?.visibility = View.GONE
                    binding.pgbLoading.show()
                }
                is DetailGatewayUiState.Success -> {
                    binding.pgbLoading.hide()
                    gateway = it.gateway
                    binding.txvSerialNumber.text = gateway.serialNumber
                    binding.txvMac.text = gateway.config.mac
                    binding.txvSSID.text = binding.root.context.getString(R.string.ssid, gateway.config.SSID)
                    binding.txvPIN.text = binding.root.context.getString(R.string.pin, gateway.pin)
                    binding.txvKernelRev.text = binding.root.context.getString(R.string.kernel, gateway.config.kernelRevision)
                    binding.txvVersion.text = binding.root.context.getString(R.string.version, gateway.config.version)
                    hashSetText()

                    binding.chpStatus.text = gateway.connection.status
                    binding.chpStatus.chipBackgroundColor = connectionStatusColor(binding.root.context, gateway.connection.status)

                    if(gateway.connection.status == Constants.Gateway.ONLINE){
                        binding.txvIp?.visibility = View.VISIBLE
                        binding.txvPing?.visibility = View.VISIBLE
                        binding.txvDownload?.visibility = View.VISIBLE
                        binding.txvUpload?.visibility = View.VISIBLE
                        binding.txvSignal?.visibility = View.VISIBLE
                        binding.txvOffline?.visibility = View.GONE
                        binding.txvIp?.text = gateway.connection.ip
                        binding.txvPing?.text = binding.root.context.getString(R.string.ping, gateway.connection.ping)
                        binding.txvDownload?.text = binding.root.context.getString(R.string.connectionSpeed, gateway.connection.download)
                        binding.txvUpload?.text = binding.root.context.getString(R.string.connectionSpeed, gateway.connection.upload)
                        binding.txvSignal?.text = binding.root.context.getString(R.string.signal, gateway.connection.signal)




                    } else{
                        binding.txvIp?.visibility = View.GONE
                        binding.txvPing?.visibility = View.GONE
                        binding.txvDownload?.visibility = View.GONE
                        binding.txvUpload?.visibility = View.GONE
                        binding.txvSignal?.visibility = View.GONE
                        binding.txvOffline?.visibility = View.VISIBLE

                    }

                    var imv = "element_${gateway.config.kernel[0].lowercase()}"
                    binding.imv1.loadFromResource(requireContext(), imv)

                    imv = "element_${gateway.config.kernel[1].lowercase()}"
                    binding.imv2.loadFromResource(requireContext(), imv)
                     imv = "element_${gateway.config.kernel[2].lowercase()}"
                    binding.imv3.loadFromResource(requireContext(), imv)

                    imv = "element_${gateway.config.kernel[3].lowercase()}"
                    binding.imv4.loadFromResource(requireContext(), imv)

                    imv = "element_${gateway.config.kernel[4].lowercase()}"
                    binding.imv5.loadFromResource(requireContext(), imv)

                    binding.txvHash.text = gateway.hash


                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun hashSetText(){
        binding.txvHash.text = gateway.hash
    }

}




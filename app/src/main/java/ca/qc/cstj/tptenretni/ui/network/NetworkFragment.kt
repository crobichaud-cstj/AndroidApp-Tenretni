package ca.qc.cstj.tptenretni.ui.network

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
import ca.qc.cstj.tptenretni.core.DateHelper
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding
import ca.qc.cstj.tptenretni.databinding.FragmentNetworkBinding
import ca.qc.cstj.tptenretni.ui.adapters.NetworkRecyclerViewAdapter
import ca.qc.cstj.tptenretni.ui.adapters.TicketRecyclerViewAdapter
import ca.qc.cstj.tptenretni.ui.tickets.TicketsUiState
import ca.qc.cstj.tptenretni.ui.tickets.TicketsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NetworkFragment : Fragment(R.layout.fragment_network) {

    private val binding: FragmentNetworkBinding by viewBinding()
    private val viewModel: NetworkViewModel by activityViewModels()

    private val networkRecyclerViewAdapter = NetworkRecyclerViewAdapter(listOf())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvNetwork.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = networkRecyclerViewAdapter
        }

        viewModel.networkUiState.onEach {
            when(it){
                is NetworkUiState.Error -> {
                    Toast.makeText(requireContext(), it.exception?.localizedMessage ?: getString(R.string.apiErrorMessage), Toast.LENGTH_SHORT).show()
                }
                NetworkUiState.Loading -> {
                    binding.rcvNetwork.visibility = View.GONE
                    binding.pgbLoading.show()
                }
                is NetworkUiState.Success -> {
                    binding.rcvNetwork.visibility = View.VISIBLE
                    binding.txvLastUpdate.visibility = View.VISIBLE
                    binding.txvNextReboot.visibility = View.VISIBLE
                    binding.textView8.visibility = View.VISIBLE
                    binding.textView9.visibility = View.VISIBLE
                    binding.imageView8.visibility = View.VISIBLE

                    networkRecyclerViewAdapter.nodes = it.network.nodes
                    networkRecyclerViewAdapter.notifyDataSetChanged()
                    binding.pgbLoading.hide()
                    binding.txvLastUpdate.text = DateHelper.formatISODate(it.network.updateDate)
                    binding.txvNextReboot.text = DateHelper.formatISODate(it.network.nextReboot)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }
}
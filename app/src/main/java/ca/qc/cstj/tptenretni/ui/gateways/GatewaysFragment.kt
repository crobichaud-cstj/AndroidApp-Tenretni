package ca.qc.cstj.tptenretni.ui.gateways

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding
import ca.qc.cstj.tptenretni.models.Gateway
import ca.qc.cstj.tptenretni.ui.adapters.GatewayRecyclerViewAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GatewaysFragment : Fragment(R.layout.fragment_gateways) {

    private val binding: FragmentGatewaysBinding by viewBinding()
    private val viewModel: GatewaysViewModel by viewModels()

    private lateinit var gatewayRecyclerViewAdapter: GatewayRecyclerViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gatewayRecyclerViewAdapter = GatewayRecyclerViewAdapter(listOf(), ::onGatewayClick)

        binding.rcvPlanets.apply {
            layoutManager = GridLayoutManager(requireContext(),Constants.RecyclerAdapters.Gateways)
            adapter = gatewayRecyclerViewAdapter

        }

        viewModel.gatewaysUiState.onEach {
            when(it) {
                is GatewaysUiState.Error -> {
                    Toast.makeText(requireContext(), it.exception?.localizedMessage ?: getString(R.string.apiErrorMessage), Toast.LENGTH_SHORT).show()
                }
                is GatewaysUiState.Success -> {
                    binding.rcvPlanets.visibility = View.VISIBLE

                    gatewayRecyclerViewAdapter.gateways = it.Gateways
                    gatewayRecyclerViewAdapter.notifyDataSetChanged()
                    binding.pgbLoading.hide()

                }
                GatewaysUiState.Loading ->  {
                    binding.rcvPlanets.visibility = View.GONE
                    binding.pgbLoading.show()
                }

            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onGatewayClick(gateway: Gateway){
        val action = GatewaysFragmentDirections.actionNavigationGatewaysToDetailGatewayFragment(gateway.href)
        findNavController().navigate(action)
    }

}
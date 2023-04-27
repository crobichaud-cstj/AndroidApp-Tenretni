package ca.qc.cstj.tptenretni.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding
import ca.qc.cstj.tptenretni.databinding.FragmentTicketsBinding

class TicketsFragment : Fragment(R.layout.fragment_tickets) {


    private val binding: FragmentTicketsBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBtn.setOnClickListener{
            val action = TicketsFragmentDirections.actionNavigationTicketsToDetailTicketFragment("https://api.andromia.science/tickets/644a7374a4f79d8a7167b1ea")
            findNavController().navigate(action)
            Toast.makeText(requireContext(), "Is here", Toast.LENGTH_LONG).show()
        }

    }
}
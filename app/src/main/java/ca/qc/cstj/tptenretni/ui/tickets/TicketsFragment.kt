package ca.qc.cstj.tptenretni.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.databinding.FragmentGatewaysBinding

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val binding: FragmentGatewaysBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
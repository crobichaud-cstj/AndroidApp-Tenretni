package ca.qc.cstj.tptenretni.ui.tickets

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cstj.tptenretni.R

class DetailTicketFragment : Fragment() {

    companion object {
        fun newInstance() = DetailTicketFragment()
    }

    private lateinit var viewModel: DetailTicketViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTicketViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
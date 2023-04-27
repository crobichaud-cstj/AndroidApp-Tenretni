package ca.qc.cstj.tptenretni.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.ColorHelper.ticketPriorityColor
import ca.qc.cstj.tptenretni.core.ColorHelper.ticketStatusColor
import ca.qc.cstj.tptenretni.core.DateHelper
import ca.qc.cstj.tptenretni.databinding.ItemTicketBinding
import ca.qc.cstj.tptenretni.models.Ticket

class TicketRecyclerViewAdapter(var tickets: List<Ticket>,
                                private val onTicketClick: (Ticket)->Unit
) : RecyclerView.Adapter<TicketRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]

        holder.bind(ticket)

        holder.itemView.setOnClickListener{
            onTicketClick(ticket)
        }
    }

    override fun getItemCount() = tickets.size



    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTicketBinding.bind(view)

        fun bind(ticket: Ticket) {

            binding.chpTicketStatus.text = ticket.status
            binding.txvTicketId.text =  binding.root.context.getString(R.string.ticket_id, ticket.ticketNumber)
            binding.chpTicketPriority.text = ticket.priority
            binding.chpTicketPriority.chipBackgroundColor = ticketPriorityColor(binding.root.context, ticket.priority)
            binding.chpTicketStatus.chipBackgroundColor = ticketStatusColor(binding.root.context, ticket.status)
            binding.txvTicketDate.text = DateHelper.formatISODate(ticket.createdDate)

        }
    }
}
package ca.qc.cstj.tptenretni.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.databinding.ItemTicketBinding
import ca.qc.cstj.tptenretni.models.Ticket
import com.bumptech.glide.Glide

class TicketRecycletViewAdapter(var tickets: List<Ticket>,
                                private val onTicketClick: (Ticket)->Unit
) : RecyclerView.Adapter<TicketRecycletViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket, holder.itemView.context, ::getString)

        holder.itemView.setOnClickListener{
            onTicketClick(ticket)
        }
    }

    override fun getItemCount() = tickets.size

    fun getString(context: Context, id: Int, arg: Any?): String {
        return if (arg == null)
            context.getString(id)
        else
            context.getString(id, arg)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTicketBinding.bind(view)

        fun bind(ticket: Ticket , context: Context, getString: (Context, Int, Any?) -> String) {

            binding.chpTicketStatus.text = ticket.status
            binding.chpTicketPriority.text = ticket.priority
            binding.txvTicketId.text =  getString(context, R.string.ticket_id, ticket.ticketNumber)

        }
    }
}
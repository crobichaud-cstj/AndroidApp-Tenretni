package ca.qc.cstj.tptenretni.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.ColorHelper
import ca.qc.cstj.tptenretni.core.DateHelper
import ca.qc.cstj.tptenretni.databinding.ItemNodeBinding
import ca.qc.cstj.tptenretni.databinding.ItemTicketBinding
import ca.qc.cstj.tptenretni.models.NetworkNode
import ca.qc.cstj.tptenretni.models.Ticket

class NetworkRecyclerViewAdapter(var nodes: List<NetworkNode>) : RecyclerView.Adapter<NetworkRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_node, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NetworkRecyclerViewAdapter.ViewHolder, position: Int) {
        val node = nodes[position]

        holder.bind(node)
    }

    override fun getItemCount() = nodes.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNodeBinding.bind(view)

        fun bind(node: NetworkNode) {

            binding.chpStatus.text = node.connection.status
            binding.txvName.text = node.name
            binding.chpStatus.chipBackgroundColor =
                ColorHelper.networkStatusColor(binding.root.context, node.connection.status)
            binding.txvIp.text = node.connection.ip
            binding.txvLatence.text = node.connection.ping.toString()
            binding.txvDownload.text = node.connection.download.toString()
            binding.txvUpload.text = node.connection.upload.toString()
            binding.txvSignal.text = node.connection.signal.toString()
        }
    }
}
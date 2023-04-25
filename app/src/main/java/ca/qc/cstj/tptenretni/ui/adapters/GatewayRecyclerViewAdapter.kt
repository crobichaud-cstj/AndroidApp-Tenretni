package ca.qc.cstj.tptenretni.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.core.Constants
import ca.qc.cstj.tptenretni.databinding.ItemGatewayBinding
import ca.qc.cstj.tptenretni.models.Gateway

class GatewayRecyclerViewAdapter(
    var gateways: List<Gateway> = listOf()
) : RecyclerView.Adapter<GatewayRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGatewayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planet = gateways[position]
        holder.bind(planet, holder.itemView.context, ::getString)
    }

    override fun getItemCount() = gateways.size

    fun getString(context: Context, id: Int, arg: Any?): String {
        return if (arg == null)
            context.getString(id)
        else
            context.getString(id, arg)
    }

    inner class ViewHolder(private val binding: ItemGatewayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gateway: Gateway, context: Context, getString: (Context, Int, Any?) -> String) {
            binding.txvSerialNumber.text = gateway.serialNumber

            if (gateway.connection.status == Constants.Gateway.ONLINE) {
                binding.txvPing.text = getString(context, R.string.ping, gateway.connection.ping)
                binding.txvDownload.text =
                    getString(context, R.string.connectionSpeed, gateway.connection.download)
                binding.txvUpload.text =
                    getString(context, R.string.connectionSpeed, gateway.connection.upload)
                binding.chpStatus.text = gateway.connection.status
                binding.chpStatus.chipBackgroundColor =
                    ColorStateList.valueOf(context.getColor(R.color.gateway_status_online))
                binding.txvOffline.visibility = View.GONE
            } else {
                binding.txvPing.visibility = View.GONE
                binding.txvDownload.visibility = View.GONE
                binding.txvUpload.visibility = View.GONE
                binding.imgPing.visibility = View.GONE
                binding.imgDownload.visibility = View.GONE
                binding.imgUpload.visibility = View.GONE
                binding.chpStatus.text = gateway.connection.status
                binding.chpStatus.chipBackgroundColor =
                    ColorStateList.valueOf(context.getColor(R.color.gateway_status_offline))
                binding.txvOffline.visibility = View.VISIBLE
            }

        }
    }


}
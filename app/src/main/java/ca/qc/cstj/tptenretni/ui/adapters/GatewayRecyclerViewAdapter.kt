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
    var gateways: List<Gateway> = listOf(),
    private val onGatewayClick: (Gateway)->Unit
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
        val gateway = gateways[position]
        holder.bind(gateway)

        holder.itemView.setOnClickListener{
            onGatewayClick(gateway)
        }
    }

    override fun getItemCount() = gateways.size


    inner class ViewHolder(private val binding: ItemGatewayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gateway: Gateway) {
            with(binding) {

                txvSerialNumber.text = gateway.serialNumber

                if (gateway.connection.status == Constants.Gateway.ONLINE) {
                    txvPing.text = root.context.getString(R.string.ping, gateway.connection.ping)
                    txvDownload.text =
                        root.context.getString(
                            R.string.connectionSpeed,
                            gateway.connection.download
                        )
                    txvUpload.text =
                        root.context.getString(R.string.connectionSpeed, gateway.connection.upload)
                    chpStatus.text = gateway.connection.status
                    chpStatus.chipBackgroundColor =
                        ColorStateList.valueOf(root.context.getColor(R.color.gateway_status_online))
                    txvOffline.visibility = View.GONE
                } else {
                    txvPing.visibility = View.GONE
                    txvDownload.visibility = View.GONE
                    txvUpload.visibility = View.GONE
                    imgPing.visibility = View.GONE
                    imgDownload.visibility = View.GONE
                    imgUpload.visibility = View.GONE
                    chpStatus.text = gateway.connection.status
                    chpStatus.chipBackgroundColor =
                        ColorStateList.valueOf(root.context.getColor(R.color.gateway_status_offline))
                    txvOffline.visibility = View.VISIBLE
                }
            }

        }
    }


}
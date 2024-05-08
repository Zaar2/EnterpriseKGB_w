package com.zaar.meatkgb2_w.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zaar.meatkgb2_w.R
import com.zaar.meatkgb2_w.data.entity.RecordUi

open class RecordsRecViewAdapter(
    private val items: List<RecordUi>,
    private var itemOnClickListener: ItemOnClickListener,
): RecyclerView.Adapter<RecordsRecViewAdapter.MyViewHolder>() {


    interface ItemOnClickListener {
        fun onItemClick(idRecord: Long)
    }

    class MyViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val idRecord: TextView = itemView.findViewById(R.id.tv_id_recView)
        val dateProduced: TextView = itemView.findViewById(R.id.tv_date_produced_recView)
        val hourProduced: TextView = itemView.findViewById(R.id.tv_time_produced_recView)
        val nameProduct: TextView = itemView.findViewById(R.id.tv_name_product_recView)
        val count: TextView = itemView.findViewById(R.id.tv_count_recView)
        val checkBox: CheckBox? = itemView.findViewById(R.id.checkbox_idDB)
        val me: TextView = itemView.findViewById(R.id.tv_me_recView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.label_item_list_view, parent, false)
        itemView.isClickable = true
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            items[position].also { recordUi ->
                idRecord.text = recordUi.id.toString()
                dateProduced.text = recordUi.dateProduced
                hourProduced.text = recordUi.timeProduced.toString()
                nameProduct.text = recordUi.productName
                count.text = recordUi.count.toString()
                me.text = recordUi.me

                itemView.setOnClickListener { _: View? ->
                    itemOnClickListener.onItemClick(
                        recordUi.id
                    )
                }
            }
        }
    }
}
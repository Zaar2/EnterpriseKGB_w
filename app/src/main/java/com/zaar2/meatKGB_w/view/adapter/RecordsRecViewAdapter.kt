package com.zaar2.meatKGB_w.view.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zaar2.meatKGB_w.R
import com.zaar2.meatKGB_w.model.entityUi.RecordUi

open class RecordsRecViewAdapter(
    private val items: List<RecordUi>,
    private var itemOnClickListener: ItemOnClickListener,
): RecyclerView.Adapter<RecordsRecViewAdapter.MyViewHolder>() {


    interface ItemOnClickListener {
        fun onItemClick(record: RecordUi, isSelected: Boolean, )
    }

    class MyViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val idRecord: TextView = itemView.findViewById(R.id.tv_id_recView)
        val dateProduced: TextView = itemView.findViewById(R.id.tv_date_produced_recView)
        val hourProduced: TextView = itemView.findViewById(R.id.tv_time_produced_recView)
        val nameProduct: TextView = itemView.findViewById(R.id.tv_name_product_recView)
        val count: TextView = itemView.findViewById(R.id.tv_count_recView)
//        val checkBox: CheckBox? = itemView.findViewById(R.id.checkbox_idDB)
        val me: TextView = itemView.findViewById(R.id.tv_me_recView)
        var isSelected = false
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
                isSelected = recordUi.isSelected
                mSetBackground(itemView, isSelected)

                itemView.setOnClickListener {
                    isSelected = !isSelected
                    recordUi.isSelected = isSelected
                    mSetBackground(it, isSelected)
                    items.forEachIndexed { index, item ->
                        if (index != position) {
                            if (item.isSelected) {
                                item.isSelected = false
                                notifyItemChanged(index)
                            }
                        }
                    }
                    itemOnClickListener.onItemClick(
                        recordUi, isSelected
                    )
                }
            }
        }
    }

    private fun mSetBackground(it:View, isSelected: Boolean) {
        when (isSelected) {
            true -> it.setBackgroundColor(it.context.getColor(R.color.white_with_alpha20))
            false -> it.background = with(TypedValue()) {
                it.context.theme.resolveAttribute(
                    androidx.appcompat.R.attr.selectableItemBackground, this, true
                )
                ContextCompat.getDrawable(it.context, resourceId)
            }
        }
    }
}
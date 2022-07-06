package com.trifonovkv.production.ui.journal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trifonovkv.production.R
import java.text.SimpleDateFormat


class JournalRecyclerAdapter(private val entries: List<ProductionEntry>) :
    RecyclerView.Adapter<JournalRecyclerAdapter.MyViewHolder>() {

    private lateinit var mContext: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewAdry: TextView = itemView.findViewById(R.id.textViewAdry)
        val textViewAfresh: TextView = itemView.findViewById(R.id.textViewAfresh)
        val textViewAfrost: TextView = itemView.findViewById(R.id.textViewAfrost)
        val textViewAfruit: TextView = itemView.findViewById(R.id.textViewAfruit)
        val textViewAlco: TextView = itemView.findViewById(R.id.textViewAlco)
        val textViewAmez: TextView = itemView.findViewById(R.id.textViewAmez)
        val textViewHolod3: TextView = itemView.findViewById(R.id.textViewHolod3)
        val textViewTotal: TextView = itemView.findViewById(R.id.textViewTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewDate.text = SimpleDateFormat("dd.MM.yyyy").format(entries[position].date)
        holder.textViewAdry.text = entries[position].adry.toString()
        holder.textViewAfresh.text = entries[position].afresh.toString()
        holder.textViewAfrost.text = entries[position].afrost.toString()
        holder.textViewAfruit.text = entries[position].afruit.toString()
        holder.textViewAlco.text = entries[position].alco.toString()
        holder.textViewAmez.text = entries[position].amez.toString()
        holder.textViewHolod3.text = entries[position].holod3.toString()
        holder.textViewTotal.text = entries[position].total.toString()

        val color = if (position % 2 == 0) {
            ContextCompat.getColor(mContext, R.color.list_color)
        } else {
            ContextCompat.getColor(mContext, R.color.dark_gray)
        }
        holder.itemView.setBackgroundColor(color)
    }

    override fun getItemCount() = entries.size
}
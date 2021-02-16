package com.example.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryStatusAdapter(val context: Context, val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_history_item_main = view.ll_history_item_main
        val tvPosition = view.tvPosition
        val tvItem = view.tvItem
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_history_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date
        if (position % 2 == 0)
        {
            holder.ll_history_item_main.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else
        {
            holder.ll_history_item_main.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }
    }
}

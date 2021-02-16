package com.example.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_exercice.*
import kotlinx.android.synthetic.main.activity_exercice.view.*
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val context: Context, val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llItem = view.llItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ExerciseModel = items[position]
        holder.llItem.tvItem.text = item.getId().toString()
        if (item.getIsSelected()) {
            holder.llItem.tvItem.background = ContextCompat.getDrawable(
                context,
                R.drawable.item_circular_thin_color_accent_border
            )
            holder.llItem.tvItem.setTextColor(Color.parseColor("#212121"))
        } else if (item.getIsCompleted()) {
            holder.llItem.tvItem.background = ContextCompat.getDrawable(
                context,
                R.drawable.item_circular_color_accent_background
            )
            holder.llItem.tvItem.setTextColor(Color.parseColor("#212121"))
        }
        else{
            holder.llItem.tvItem.background = ContextCompat.getDrawable(
                context,
                R.drawable.item_circular_color_gray_background
            )
            holder.llItem.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

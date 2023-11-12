package com.example.dailyvita.presentation.health_concern

import android.annotation.SuppressLint
import com.example.dailyvita.R
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyvita.data.models.HealthConcern

class HealthConcernItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var name: TextView = itemView.findViewById(R.id.itemTextView)
//  private var image: ImageView = itemView.findViewById(R.id.dragImage)

    @SuppressLint("ClickableViewAccessibility")
    fun bind(data: HealthConcern) {
        name.text = data.name
    }

    companion object {
        fun create(parent: ViewGroup): HealthConcernItemViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.health_concern_item, parent, false)
            return HealthConcernItemViewHolder(view)
        }
    }
}
package com.example.dailyvita.presentation.health_concern

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.dailyvita.data.models.HealthConcern

class HealthConcernAdapter(diffCallback: DiffUtil.ItemCallback<HealthConcern>) : ListAdapter<HealthConcern, HealthConcernItemViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthConcernItemViewHolder {
        return HealthConcernItemViewHolder.create(parent)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: HealthConcernItemViewHolder, position: Int) {
        val current: HealthConcern = getItem(position)
        holder.bind(current)
    }

    class HealthConcernDiff : DiffUtil.ItemCallback<HealthConcern>() {
        override fun areItemsTheSame(oldItem: HealthConcern, newItem: HealthConcern): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HealthConcern, newItem: HealthConcern): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
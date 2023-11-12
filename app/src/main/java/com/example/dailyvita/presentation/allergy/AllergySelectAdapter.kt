package com.example.dailyvita.presentation.allergy

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyvita.data.models.Allergy

class AllergySelectAdapter(private val onTextChangeListener: (String) -> Unit) : RecyclerView.Adapter<AllergyItemViewHolder>() {

    private var items: List<Allergy?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllergyItemViewHolder {
        return AllergyItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AllergyItemViewHolder, position: Int) {
        val current: Allergy? = items[position]
        holder.bind(current, onTextChangeListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateAdapter(data: List<Allergy?>) {
        items = data
        notifyDataSetChanged()
    }
}
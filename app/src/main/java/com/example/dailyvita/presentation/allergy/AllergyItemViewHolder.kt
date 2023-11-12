package com.example.dailyvita.presentation.allergy

import com.example.dailyvita.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyvita.data.models.Allergies
import com.example.dailyvita.data.models.Allergy
import com.example.dailyvita.data.models.HealthConcern

class AllergyItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private var name: TextView = itemView.findViewById(R.id.itemTextView)
    private var editText: EditText = itemView.findViewById(R.id.editText)

    fun bind(data: Allergy?, onTextChangeListener: (String) -> Unit) {
        name.visibility = if (data != null) View.VISIBLE else View.GONE
        editText.visibility = if (data == null) View.VISIBLE else View.GONE
        name.text = data?.name ?: ""
        if (editText.isVisible) {
            editText.requestFocus()
        }
        editText.addTextChangedListener {
            onTextChangeListener(it.toString())
        }
    }

    companion object {
        fun create(parent: ViewGroup): AllergyItemViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.allergy_item, parent, false)
            return AllergyItemViewHolder(view)
        }
    }
}
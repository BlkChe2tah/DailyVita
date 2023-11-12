package com.example.dailyvita.presentation.diet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.example.dailyvita.R
import com.example.dailyvita.data.models.Diet
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener


class DietListAdapter(
    private val context: Context,
    private val onItemCheckedListener: () -> Unit,
    private val onItemClickListener: (View, String) -> Unit
) : ArrayAdapter<Diet>(context, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val diet: Diet? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.diet_item, parent, false)
        }
        val tvName = convertView?.findViewById<View>(R.id.dietName) as TextView
        val tvCheckBox = convertView.findViewById<View>(R.id.dietCheckBox) as CheckBox
        val tvInfoBtn = convertView.findViewById<View>(R.id.infoBtn) as Button
        tvName.text = diet?.name
        tvCheckBox.isChecked = diet?.checked ?: false
        tvCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            diet?.let {
                it.checked = !it.checked
            }
            onItemCheckedListener()
        }
        tvInfoBtn.setOnClickListener {
            onItemClickListener(it, diet?.toolTip ?: "")
        }
        return convertView
    }

}
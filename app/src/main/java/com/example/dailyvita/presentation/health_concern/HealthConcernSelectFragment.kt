package com.example.dailyvita.presentation.health_concern

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.dailyvita.R
import com.example.dailyvita.data.data_source.assets.HealthConcernsDataSource
import com.example.dailyvita.data.models.HealthConcern
import com.example.dailyvita.data.use_cases.HealthConcernSelectUseCaseImpl
import com.example.dailyvita.databinding.FragmentHealthConcernSelectBinding
import com.example.dailyvita.presentation.view_models.HealthConcernsSelectViewModel
import com.example.dailyvita.presentation.view_models.HealthConcernsSelectViewModelFactory
import com.example.dailyvita.presentation.view_models.MainActivityViewModel
import com.example.dailyvita.utils.UiState
import com.google.android.material.chip.Chip


class HealthConcernSelectFragment : Fragment() {

    private var _binding: FragmentHealthConcernSelectBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HealthConcernsSelectViewModel
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var adapter: HealthConcernAdapter

    private var saveData: List<HealthConcern> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HealthConcernsSelectViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        adapter = HealthConcernAdapter(HealthConcernAdapter.HealthConcernDiff())
        if (viewModel.uiState.value !is UiState.Success) {
            viewModel.loadHealthConcerns()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHealthConcernSelectBinding.inflate(inflater, container, false)
        setTitle()
        val callback = ItemMoveCallback { start, end ->
            viewModel.swapItem(start, end)
            adapter.notifyItemMoved(start, end)
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        binding.recycler.adapter = adapter
        binding.recycler.isNestedScrollingEnabled = false
        itemTouchHelper.attachToRecyclerView(binding.recycler)
        binding.btnFrame.back.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.btnFrame.next.setOnClickListener {
            mainViewModel.setHealthConcerns(saveData)
            Navigation.findNavController(it).navigate(R.id.action_healthConcernSelectFragment_to_dietSelectFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(this.viewLifecycleOwner) { it ->
            binding.contentFrame.visibility = if (it is UiState.Success) View.VISIBLE else View.GONE
            binding.loadingIndicator.visibility = if (it is UiState.Loading) View.VISIBLE else View.GONE
            if (it is UiState.Error) {
                Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.healthConcernItems.observe(this.viewLifecycleOwner) { it ->
            if (it.isNotEmpty()) {
                binding.chipGp.removeAllViews()
                it.forEach { data ->
                    binding.chipGp.addView(
                        loadChipView(data)
                    )
                }
            }
        }
        viewModel.selectedItems.observe(this.viewLifecycleOwner) { it ->
            val isVisible = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            binding.recyclerTitle.visibility = isVisible
            binding.recycler.visibility = isVisible
            adapter.submitList(it)
            saveData = it
        }
        viewModel.isNextButtonEnable.observe(this.viewLifecycleOwner) { it ->
            binding.btnFrame.next.isEnabled = it
        }
    }

    private val onChipItemClick: (HealthConcern, Boolean) -> Unit = { data, isSelected ->
        if (isSelected) {
            viewModel.addItem(data)
        } else {
            viewModel.removeItem(data)
        }
    }

    private fun loadChipView(data: HealthConcern) : Chip {
        val chipView = layoutInflater.inflate(R.layout.chip_item, null) as Chip
        return chipView.apply {
            id = data.id
            text = data.name
            isChecked = data.isSelected
            setOnClickListener {
                data.isSelected = !data.isSelected
                onChipItemClick(data, data.isSelected)
            }
        }
    }

    private fun setTitle() {
        val title1 = resources.getString(R.string.select_health_concern)
        val title2 = resources.getString(R.string.up_to_5)
        val builder = SpannableStringBuilder("$title1*")
        builder.setSpan(ForegroundColorSpan(Color.RED), title1.length, title1.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.append("\n$title2")
        binding.title.text = builder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






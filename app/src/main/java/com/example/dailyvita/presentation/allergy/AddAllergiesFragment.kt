package com.example.dailyvita.presentation.allergy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.dailyvita.R
import com.example.dailyvita.data.data_source.assets.AllergiesDataSource
import com.example.dailyvita.data.models.Allergy
import com.example.dailyvita.data.use_cases.AllergySelectUseCaseImpl
import com.example.dailyvita.databinding.FragmentAddAllergiesBinding
import com.example.dailyvita.presentation.view_models.AllergiesSelectViewModel
import com.example.dailyvita.presentation.view_models.AllergiesSelectViewModelFactory
import com.example.dailyvita.presentation.view_models.HealthConcernsSelectViewModel
import com.example.dailyvita.presentation.view_models.MainActivityViewModel
import com.example.dailyvita.utils.UiState


class AddAllergiesFragment : Fragment() {

    private var _binding: FragmentAddAllergiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AllergiesSelectViewModel
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var searchAdapter: AllergySelectAdapter

    private var saveData: List<Allergy?> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AllergiesSelectViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        context?.let {
            adapter = ArrayAdapter(it, R.layout.filter_item, R.id.filterItemText)
        }
        val textChangedListener: (String) -> Unit = { text ->
            viewModel.setText(text)
        }
        searchAdapter = AllergySelectAdapter(textChangedListener)
        if (viewModel.allergies.value?.isEmpty() == true) {
            viewModel.loadAllergies()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAllergiesBinding.inflate(inflater, container, false)
        binding.list.adapter = adapter
        binding.list.setOnItemClickListener { parent, view, position, id ->
            viewModel.addItem(position)
        }
        binding.recycler.adapter = searchAdapter
        binding.btnFrame.back.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.btnFrame.next.setOnClickListener {
            mainViewModel.allergies = saveData
            Navigation.findNavController(it).navigate(R.id.action_addAllergiesFragment_to_otherInformationFragment)
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
        viewModel.selectedAllergy.observe(this.viewLifecycleOwner) { items ->
            searchAdapter.updateAdapter(items)
            saveData = items.filter { it?.id != null }
        }
        viewModel.filteredAllergy.observe(this.viewLifecycleOwner) { it ->
            adapter.clear()
            adapter.addAll(it.map { it.name })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
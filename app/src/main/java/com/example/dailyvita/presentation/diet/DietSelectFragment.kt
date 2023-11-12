package com.example.dailyvita.presentation.diet

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.dailyvita.R
import com.example.dailyvita.data.data_source.assets.DietsDataSource
import com.example.dailyvita.data.models.Diet
import com.example.dailyvita.data.use_cases.DietSelectUseCaseImpl
import com.example.dailyvita.databinding.FragmentDietSelectBinding
import com.example.dailyvita.presentation.view_models.DietSelectViewModel
import com.example.dailyvita.presentation.view_models.DietsSelectViewModelFactory
import com.example.dailyvita.presentation.view_models.HealthConcernsSelectViewModel
import com.example.dailyvita.presentation.view_models.MainActivityViewModel
import com.example.dailyvita.utils.UiState
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

class DietSelectFragment : Fragment() {

    private var _binding: FragmentDietSelectBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DietSelectViewModel
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var adapter: DietListAdapter

    private var saveData: List<Diet> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DietSelectViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        context?.let { mContext ->
            adapter = DietListAdapter(mContext, {
                viewModel.updateDiet()
            }) { view, tooltip ->
                val balloon = Balloon.Builder(view.context).apply {
                    arrowSize = 24
                    width = 350
                    height = BalloonSizeSpec.WRAP
                    textSize = 15f
                    arrowOrientation = ArrowOrientation.START
                    cornerRadius = 4f
                    text = tooltip
                    paddingTop = 8
                    paddingLeft = 8
                    paddingRight = 8
                    paddingBottom = 8
                    backgroundColor = ContextCompat.getColor(view.context, R.color.md_theme_light_surface)
                    textColor = ContextCompat.getColor(view.context, R.color.md_theme_light_onSurface)
                    balloonAnimation = BalloonAnimation.FADE
                    lifecycleOwner = this@DietSelectFragment.viewLifecycleOwner
                }.build()
                balloon.showAlignEnd(view)
                balloon.dismissWithDelay(1500)
            }
        }
        if (viewModel.diets.value?.isEmpty() == true) {
            viewModel.loadDiets()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietSelectBinding.inflate(inflater, container, false)
        setTitle()
        binding.dietListView.adapter = adapter
        binding.btnFrame.back.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.btnFrame.next.setOnClickListener {
            mainViewModel.diets = saveData
            Navigation.findNavController(it).navigate(R.id.action_dietSelectFragment_to_addAllergiesFragment)
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
        viewModel.diets.observe(this.viewLifecycleOwner) { it ->
            if (it.isNotEmpty()) {
                adapter.clear()
                adapter.addAll(it)
            }
        }
        viewModel.selectedDiets.observe(this.viewLifecycleOwner) { it ->
            saveData = it
        }
        viewModel.isNextButtonEnable.observe(this.viewLifecycleOwner) { it ->
            binding.btnFrame.next.isEnabled = it
        }
    }



    private fun setTitle() {
        val title = resources.getString(R.string.select_diets)
        val builder = SpannableStringBuilder("$title*")
        builder.setSpan(ForegroundColorSpan(Color.RED), title.length, title.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.title.text = builder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
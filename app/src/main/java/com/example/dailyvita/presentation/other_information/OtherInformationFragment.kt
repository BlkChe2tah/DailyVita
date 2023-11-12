package com.example.dailyvita.presentation.other_information

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dailyvita.R
import com.example.dailyvita.databinding.FragmentOtherInformationBinding
import com.example.dailyvita.presentation.view_models.MainActivityViewModel

class OtherInformationFragment : Fragment() {

    private var _binding: FragmentOtherInformationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherInformationBinding.inflate(inflater, container, false)
        setTitle()
        setTitle2()
        setTitle3()
        binding.radioGp.setOnCheckedChangeListener { group, checkedId ->
            viewModel.isDailyExposure = checkedId == R.id.sunYes
        }
        binding.radioGp2.setOnCheckedChangeListener { group, checkedId ->
            viewModel.isSmoke = checkedId == R.id.smokeYes
        }
        binding.radioGp3.setOnCheckedChangeListener { group, checkedId ->
             val result =  when(checkedId) {
                R.id.alcoholic1 -> 1
                R.id.alcoholic2 -> 2
                R.id.alcoholic3 -> 3
                 else -> 1
            }
            viewModel.alcholState = result
        }
        binding.done.setOnClickListener {
            Log.d("OUT_PUT", viewModel.loadOutput())
            Toast.makeText(this.context, "Please check data on LogCat!!", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioGp.check(if (viewModel.isDailyExposure) R.id.sunYes else R.id.sunNo)
        binding.radioGp2.check(if (viewModel.isSmoke) R.id.smokeYes else R.id.smokeNo)
        binding.radioGp3.check(
            when(viewModel.alcholState) {
                1 -> R.id.alcoholic1
                2 -> R.id.alcoholic2
                3 -> R.id.alcoholic3
                else -> R.id.alcoholic1
            }
        )
    }

    private fun setTitle() {
        val title = resources.getString(R.string.daily_exposure)
        val builder = SpannableStringBuilder("$title*")
        builder.setSpan(ForegroundColorSpan(Color.RED), title.length, title.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.title.text = builder
    }

    private fun setTitle2() {
        val title = resources.getString(R.string.do_you_smoke)
        val builder = SpannableStringBuilder("$title*")
        builder.setSpan(ForegroundColorSpan(Color.RED), title.length, title.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.title2.text = builder
    }

    private fun setTitle3() {
        val title = resources.getString(R.string.average_alcoholic)
        val builder = SpannableStringBuilder("$title*")
        builder.setSpan(ForegroundColorSpan(Color.RED), title.length, title.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.title3.text = builder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
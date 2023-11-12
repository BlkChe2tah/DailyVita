package com.example.dailyvita.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dailyvita.R
import com.example.dailyvita.data.data_source.assets.AllergiesDataSource
import com.example.dailyvita.data.data_source.assets.DietsDataSource
import com.example.dailyvita.data.data_source.assets.HealthConcernsDataSource
import com.example.dailyvita.data.use_cases.AllergySelectUseCaseImpl
import com.example.dailyvita.data.use_cases.DietSelectUseCaseImpl
import com.example.dailyvita.data.use_cases.HealthConcernSelectUseCaseImpl
import com.example.dailyvita.databinding.ActivityMainBinding
import com.example.dailyvita.presentation.view_models.AllergiesSelectViewModel
import com.example.dailyvita.presentation.view_models.AllergiesSelectViewModelFactory
import com.example.dailyvita.presentation.view_models.DietSelectViewModel
import com.example.dailyvita.presentation.view_models.DietsSelectViewModelFactory
import com.example.dailyvita.presentation.view_models.HealthConcernsSelectViewModel
import com.example.dailyvita.presentation.view_models.HealthConcernsSelectViewModelFactory
import com.example.dailyvita.presentation.view_models.MainActivityViewModel
import com.example.dailyvita.presentation.view_models.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val healthConcernDataSource = HealthConcernsDataSource(assets)
        val dietDataSource = DietsDataSource(assets)
        val allergyDataSource = AllergiesDataSource(assets)
        val healthConcernUseCase = HealthConcernSelectUseCaseImpl(healthConcernDataSource)
        val dietUseCase = DietSelectUseCaseImpl(dietDataSource)
        val allergyUseCase = AllergySelectUseCaseImpl(allergyDataSource)
        ViewModelProvider(this, HealthConcernsSelectViewModelFactory(healthConcernUseCase))[HealthConcernsSelectViewModel::class.java]
        ViewModelProvider(this, DietsSelectViewModelFactory(dietUseCase))[DietSelectViewModel::class.java]
        ViewModelProvider(this, AllergiesSelectViewModelFactory(allergyUseCase))[AllergiesSelectViewModel::class.java]
        val viewModel = ViewModelProvider(this, MainActivityViewModelFactory())[MainActivityViewModel::class.java]
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            binding.progress.visibility = if (destination.id != R.id.WelcomeFragment) View.VISIBLE else View.GONE
        }
        viewModel.progress.observe(this){
            it?.let { value ->
                binding.progress.setProgress(value, true)
            }
        }
    }
}


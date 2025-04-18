package com.example.walmartcodingassesment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartcodingassesment.data.repo.DataRepository
import com.example.walmartcodingassesment.domain.model.CountryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val countries: StateFlow<List<CountryItem>> =
        dataRepository.allCountries.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    //
    fun fetchCountries() {
        viewModelScope.launch {
            dataRepository.fetchCountry()
        }
    }
}

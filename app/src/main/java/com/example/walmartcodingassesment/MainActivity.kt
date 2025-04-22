package com.example.walmartcodingassesment

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.widget.addTextChangedListener
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcodingassesment.domain.model.CountryItem
import com.example.walmartcodingassesment.ui.adapter.CountryAdapter
import com.example.walmartcodingassesment.ui.theme.WalmartCodingAssesmentTheme
import com.example.walmartcodingassesment.ui.viewmodel.CountryViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var adapter: CountryAdapter
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_layout)

        adapter = CountryAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.countryRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        recyclerView.adapter = adapter


        // Observes the countries from the ViewModel
        lifecycleScope.launch {
            viewModel.countries.collectLatest {
                adapter.submitCountryList(it)
            }
        }

        val searchView = findViewById<TextInputEditText>(R.id.countrySearchBar)
        searchView.addTextChangedListener { adapter.filter.filter(it.toString()) }

    }
}


@Composable
fun CountryListScreen(
    viewModel: CountryViewModel = hiltViewModel()
) {

    val countryList by viewModel.countries.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCountries()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(countryList) { country ->
            CountryCard(country)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CountryCard(country: CountryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = "${country.name}, ", style = MaterialTheme.typography.bodySmall)
            Text(text = "${country.region}", style = MaterialTheme.typography.bodySmall)
            Text(text = "     ${country.code}", style = MaterialTheme.typography.bodySmall)
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = "${country.capital}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


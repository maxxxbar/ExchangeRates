package ws.worldshine.exchangerates.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ws.worldshine.exchangerates.database.Database
import ws.worldshine.exchangerates.datasource.CurrenciesRepository
import ws.worldshine.exchangerates.entities.CurrencyItem
import ws.worldshine.exchangerates.network.Rest
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@ExperimentalPagingApi
class MainActivityViewModel @Inject constructor(rest: Rest, database: Database) : ViewModel() {

    private val repository = CurrenciesRepository(database, rest)
    fun getData(): LiveData<PagingData<CurrencyItem>> {
        return repository.getResult().cachedIn(viewModelScope)
    }

    fun convertValue(baseCurrency: Double?, value: Double?, defaultValue: Double): Double {
        return if (baseCurrency != null && value != null) {
            baseCurrency / value
        } else {
            defaultValue
        }
    }

    fun roundValue(value: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(value)
    }
}
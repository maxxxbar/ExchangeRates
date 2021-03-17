package ws.worldshine.exchangerates.datasource

import androidx.lifecycle.LiveData
import androidx.paging.*
import ws.worldshine.exchangerates.database.Database
import ws.worldshine.exchangerates.entities.CurrencyItem
import ws.worldshine.exchangerates.network.Rest

@ExperimentalPagingApi
class CurrenciesRepository(
    database: Database,
    rest: Rest
) {
    private companion object {
        private const val NETWORK_PAGE = 50
    }

    private val config = PagingConfig(pageSize = NETWORK_PAGE, enablePlaceholders = true)

    private val remoteMediator = CurrencyRemoteMediator(rest, database)
    private val pagingSourceFactory = { database.currenciesDao().getAllCurrencies() }

    fun getResult(): LiveData<PagingData<CurrencyItem>> {
        return Pager(
            config = config,
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

}
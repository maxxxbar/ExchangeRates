package ws.worldshine.exchangerates.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ws.worldshine.exchangerates.database.Database
import ws.worldshine.exchangerates.entities.CurrencyItem
import ws.worldshine.exchangerates.entities.RemoteKeys
import ws.worldshine.exchangerates.network.Rest
import ws.worldshine.exchangerates.utils.getCurrenciesList
import java.io.IOException

@ExperimentalPagingApi
class CurrencyRemoteMediator(
    private val rest: Rest,
    private val database: Database
) : RemoteMediator<Int, CurrencyItem>() {
    private companion object {
        private const val START_PAGE = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CurrencyItem>
    ): MediatorResult {
        try {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true,
                )
                LoadType.APPEND -> {
                    val remoteKeys = database.withTransaction {
                        state.lastItemOrNull()?.numCode?.toInt()?.let {
                            database.remoteKeysDao().remoteKeysId(it)
                        }
                    }
                    if (remoteKeys?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKeys.nextKey
                }
            }
            val page = pageKey ?: START_PAGE
            return withContext(Dispatchers.IO) {
                val currencies = rest.getCurrencies().body()?.valuteJsonObject
                val currenciesList = getCurrenciesList(currencies)
                val endOfPaginationReached = currenciesList.isEmpty()
                if (loadType == LoadType.REFRESH) {
                    database.currenciesDao().clearTable()
                    database.remoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = currenciesList.map {
                    it.currencyItem?.numCode?.toInt()?.let { id ->
                        RemoteKeys(
                            id = id,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                }
                currenciesList.map {
                    it.currencyItem?.let { currencyItem ->
                        database.currenciesDao().insertAll(currencyItem)
                    }
                }
                database.remoteKeysDao().insertAll(keys)
                MediatorResult.Success(true)
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}


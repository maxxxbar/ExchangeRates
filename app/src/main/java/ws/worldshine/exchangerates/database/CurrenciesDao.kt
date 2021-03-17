package ws.worldshine.exchangerates.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ws.worldshine.exchangerates.entities.CurrencyItem

@Dao
interface CurrenciesDao {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): PagingSource<Int, CurrencyItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyItem: CurrencyItem)

    @Query("DELETE FROM currencies")
    suspend fun clearTable()
}
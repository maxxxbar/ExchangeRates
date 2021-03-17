package ws.worldshine.exchangerates.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ws.worldshine.exchangerates.entities.CurrencyItem
import ws.worldshine.exchangerates.entities.RemoteKeys

@Database(
    entities = [CurrencyItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun currenciesDao(): CurrenciesDao
}
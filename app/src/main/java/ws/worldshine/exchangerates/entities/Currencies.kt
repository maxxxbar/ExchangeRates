package ws.worldshine.exchangerates.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class Currencies(
    @SerializedName("Date")
    val date: String? = null,
    @SerializedName("PreviousDate")
    val previousDate: String? = null,
    @SerializedName("PreviousURL")
    val previousURL: String? = null,
    @SerializedName("Timestamp")
    val timestamp: String? = null,
    @SerializedName("Valute")
    val valuteJsonObject: JsonObject? = null
)

data class Valute(
    val name: String? = null,
    val currencyItem: CurrencyItem? = null
)

@Entity(tableName = "currencies")
data class CurrencyItem(
    @SerializedName("CharCode")
    val charCode: String? = null,
    @SerializedName("ID")
    val iD: String? = null,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("Nominal")
    val nominal: Int? = null,
    @SerializedName("NumCode")
    @PrimaryKey val numCode: String,
    @SerializedName("Previous")
    val previous: Double? = null,
    @SerializedName("Value")
    val value: Double? = null
)

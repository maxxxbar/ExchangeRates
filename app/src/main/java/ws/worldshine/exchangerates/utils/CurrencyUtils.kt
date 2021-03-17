package ws.worldshine.exchangerates.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import ws.worldshine.exchangerates.entities.CurrencyItem
import ws.worldshine.exchangerates.entities.Valute

fun getCurrenciesList(jsonOject: JsonObject?): List<Valute> {
    if (jsonOject != null) {
        val currencyList = mutableListOf<Valute>()
        val currencyKeySet = jsonOject.keySet()
        if (currencyKeySet != null) {
            for (currencyKey in currencyKeySet) {
                val currencyJson = jsonOject.getAsJsonObject(currencyKey)
                currencyList.add(
                    Valute(
                        name = currencyKey,
                        currencyItem = Gson().fromJson(currencyJson, CurrencyItem::class.java)
                    )
                )
            }
        }
        return currencyList
    } else {
        return emptyList()
    }
}
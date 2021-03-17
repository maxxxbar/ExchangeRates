package ws.worldshine.exchangerates.network

import retrofit2.Response
import retrofit2.http.GET
import ws.worldshine.exchangerates.entities.Currencies

interface Rest {
    @GET("/daily_json.js")
    suspend fun getCurrencies(): Response<Currencies>
}
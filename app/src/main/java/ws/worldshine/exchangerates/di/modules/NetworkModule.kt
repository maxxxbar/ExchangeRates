package ws.worldshine.exchangerates.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ws.worldshine.exchangerates.network.Rest
import javax.inject.Singleton


@Module
class NetworkModule {
    private companion object {
        private const val BASE_URL = "https://cbr-xml-daily.ru"
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Rest =
        retrofit.create(Rest::class.java)

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()


}
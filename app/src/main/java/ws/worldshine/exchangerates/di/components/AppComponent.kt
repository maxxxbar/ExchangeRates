package ws.worldshine.exchangerates.di.components

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ws.worldshine.exchangerates.App
import ws.worldshine.exchangerates.di.modules.MainActivityModule
import ws.worldshine.exchangerates.di.modules.NetworkModule
import ws.worldshine.exchangerates.di.modules.RoomModule
import javax.inject.Singleton


@ExperimentalPagingApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        MainActivityModule::class,
        RoomModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}
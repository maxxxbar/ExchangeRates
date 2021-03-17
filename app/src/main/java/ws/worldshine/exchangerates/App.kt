package ws.worldshine.exchangerates

import androidx.paging.ExperimentalPagingApi
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ws.worldshine.exchangerates.di.components.DaggerAppComponent

class App : DaggerApplication() {

    @ExperimentalPagingApi
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

}
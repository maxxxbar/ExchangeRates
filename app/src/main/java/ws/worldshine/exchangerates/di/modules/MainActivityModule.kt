package ws.worldshine.exchangerates.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ws.worldshine.exchangerates.di.factories.ViewModelBuilder
import ws.worldshine.exchangerates.di.factories.ViewModelKey
import ws.worldshine.exchangerates.ui.main.MainActivity
import ws.worldshine.exchangerates.ui.main.MainActivityViewModel

@ExperimentalPagingApi
@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindVIewModel(viewModel: MainActivityViewModel): ViewModel
}
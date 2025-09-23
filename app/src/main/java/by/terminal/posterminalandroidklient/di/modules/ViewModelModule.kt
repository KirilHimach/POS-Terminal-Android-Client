package by.terminal.posterminalandroidklient.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.terminal.posterminalandroidklient.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun createMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}
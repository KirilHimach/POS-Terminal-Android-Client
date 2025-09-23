package by.terminal.posterminalandroidklient.di.base

import android.app.Application
import by.terminal.posterminalandroidklient.MainActivity
import by.terminal.posterminalandroidklient.di.modules.RemoteRepoModule
import by.terminal.posterminalandroidklient.di.modules.UseCasesModule
import by.terminal.posterminalandroidklient.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        ApiModule::class,
        RemoteRepoModule::class,
        UseCasesModule::class
    ]
)

internal interface DaggerComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): DaggerComponent
    }
}
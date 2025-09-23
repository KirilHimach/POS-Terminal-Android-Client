package by.terminal.posterminalandroidklient.di.modules

import by.terminal.posterminalandroidklient.domain.repositories.RemoteRepository
import by.terminal.posterminalandroidklient.domain.use_cases.TransactionUseCase
import dagger.Module
import dagger.Provides

@Module
internal class UseCasesModule {
    @Provides
    internal fun provideTransactionUseCase(remoteRepository: RemoteRepository) = TransactionUseCase(
        remoteRepository = remoteRepository
    )
}
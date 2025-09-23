package by.terminal.posterminalandroidklient.di.modules

import by.terminal.posterminalandroidklient.data.remote.api.TransactionApi
import by.terminal.posterminalandroidklient.data.repositories.RemoteRepositoryImpl
import by.terminal.posterminalandroidklient.data.repositories.remote.TransactionRepo
import by.terminal.posterminalandroidklient.data.repositories.remote.TransactionRepoImpl
import by.terminal.posterminalandroidklient.domain.repositories.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class RemoteRepoModule {
    @Singleton
    @Provides
    internal fun provideTransactionRemote(transactionApi: TransactionApi): TransactionRepo =
        TransactionRepoImpl(transactionApi = transactionApi)

    @Singleton
    @Provides
    internal fun provideRemoteRepo(transactionRepo: TransactionRepo): RemoteRepository =
        RemoteRepositoryImpl(transactionRepo = transactionRepo)
}
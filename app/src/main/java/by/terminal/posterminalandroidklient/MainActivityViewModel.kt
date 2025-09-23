package by.terminal.posterminalandroidklient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.terminal.posterminalandroidklient.domain.models.PaymentStatus
import by.terminal.posterminalandroidklient.domain.repositories.RemoteResult
import by.terminal.posterminalandroidklient.domain.use_cases.TransactionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainActivityViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {
    private val transactionEvent: MutableStateFlow<RemoteResult<PaymentStatus>> =
        MutableStateFlow(value = RemoteResult.Default)
    internal fun getTransactionEvent(): Flow<RemoteResult<PaymentStatus>> = transactionEvent

    internal fun sendTransaction(
        cardPan: String,
        amount: String
    ) {
        viewModelScope.launch {
            val event = transactionUseCase.buildOrderAndSend(
                cardPan = cardPan,
                amount = amount
            )
            onTransactionEventHandled(event = event)
        }
    }

    private fun onTransactionEventHandled(event: RemoteResult<PaymentStatus>) {
        viewModelScope.launch { transactionEvent.emit(event) }
    }
}
package by.terminal.posterminalandroidklient.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal inline fun <reified T> Flow<T>.observeWithActivityLifecycle(
    appCompatActivity: AppCompatActivity,
    noinline action: suspend (T) -> Unit,
    mainActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Job = appCompatActivity.lifecycleScope.launch {
    flowWithLifecycle(appCompatActivity.lifecycle, mainActiveState).collect { action(it) }
}
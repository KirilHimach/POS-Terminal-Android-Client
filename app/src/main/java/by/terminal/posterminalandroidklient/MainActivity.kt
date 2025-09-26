package by.terminal.posterminalandroidklient

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import by.terminal.posterminalandroidklient.databinding.ActivityMainBinding
import by.terminal.posterminalandroidklient.di.base.DaggerDaggerComponent
import by.terminal.posterminalandroidklient.di.modules.ViewModelFactory
import by.terminal.posterminalandroidklient.domain.models.PaymentStatus
import by.terminal.posterminalandroidklient.domain.repositories.RemoteResult
import by.terminal.posterminalandroidklient.utils.observeWithActivityLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var _mainActivityViewModel: MainActivityViewModel? = null
    private val mainActivityViewModel: MainActivityViewModel
        get() = _mainActivityViewModel ?: throw IllegalStateException("MainActivityViewModel is not found")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onInitOptions()
    }

    private fun onInitOptions() {
        initViewModel()
        initBinding()
        onBtnListener()
        onTransactionEventObserver()
        initCardPanCompleteTextView()
    }

    private fun initViewModel() {
        application.let {
            DaggerDaggerComponent.factory().create(it).inject(this)
        }
        _mainActivityViewModel = viewModelFactory.create(MainActivityViewModel::class.java)
    }

    private fun initBinding() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initCardPanCompleteTextView() {
        val items = listOf(
            "4893256874159852",
            "4598236444551559",
            "9423669412567885",
            "4896314589662514",
            "4596363214569877"
        )
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            items
        )
        binding.cardPan.apply {
            setAdapter(adapter)
            setText(items[0], false)
        }
    }

    private fun onBtnListener() {
        with(binding) {
            checkout.setOnClickListener {
                mainActivityViewModel.sendTransaction(
                    cardPan = cardPan.text.toString(),
                    amount = amount.text.toString()
                )
            }
        }
    }

    private fun onTransactionEventObserver() =
        mainActivityViewModel.getTransactionEvent().observeWithActivityLifecycle(
            appCompatActivity = this@MainActivity,
            action = ::onTransactionEventAction
        )

    private fun onTransactionEventAction(event: RemoteResult<PaymentStatus>) {
        when (event) {
            is RemoteResult.Success -> {
                onShowDialog(
                    title = event.body.status,
                    message = "AuthCode=${event.body.authCode}\nTimeStamp=${event.body.timestamp}")
            }
            is RemoteResult.Failure -> {
                onShowDialog(title = "FAILURE", message = event.e)
            }
            is RemoteResult.Error -> {
                onShowDialog(title = "ERROR", message = event.e.message.toString())
            }
            is RemoteResult.Default -> {}
        }
    }

    private fun onShowDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("OK") { dialog, which ->
                dialog.cancel()
            }
            .show()
    }
}
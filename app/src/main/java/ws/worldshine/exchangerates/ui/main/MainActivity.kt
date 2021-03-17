package ws.worldshine.exchangerates.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ws.worldshine.exchangerates.R
import ws.worldshine.exchangerates.adapters.CurrencyAdapter
import ws.worldshine.exchangerates.databinding.ActivityMainBinding
import javax.inject.Inject

@ExperimentalPagingApi
class MainActivity : DaggerAppCompatActivity() {
    /*ViewModel*/
    @Inject
    lateinit var viewModelFactor: ViewModelProvider.Factory
    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactor }

    /*ViewModel*/
    /*Adapters*/
    private val adapter = CurrencyAdapter()

    /*Adapters*/
    /*ViewBinding*/
    private lateinit var binding: ActivityMainBinding
    /*ViewBinding*/


    /*Views*/
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    /*Views*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializationViews()
        initializationRecyclerView()
        getCurrenciesList()
        updateBtnHandler()
    }

    private fun initializationViews() {
        recyclerView = binding.rvCurrenciesList
        swipeRefreshLayout = binding.srRefresh
        bottomSheetDialog =
            configurationBottomSheetDialog(this, R.layout.currency_converter_bottom_sheet)
    }

    private fun updateBtnHandler() {
        swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            lifecycleScope.launch {
                delay(3000)
                binding.srRefresh.isRefreshing = false
            }
        }
    }

    private fun initializationRecyclerView() {
        recyclerView.adapter = adapter
        adapter.setOnClickListener { value ->
            convertBottomSheetCreate(value)
        }
    }

    private fun configurationBottomSheetDialog(
        context: Context,
        @LayoutRes layout: Int
    ): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(layout)
        return bottomSheetDialog
    }

    private fun convertBottomSheetCreate(value: Double) {
        val baseCurrencyValueEditText =
            bottomSheetDialog.findViewById<TextInputEditText>(R.id.tiet_base_currency_value)
        val buttonResult = bottomSheetDialog.findViewById<Button>(R.id.btn_currency_convert)
        val textViewResult = bottomSheetDialog.findViewById<TextView>(R.id.tv_currency_result)
        buttonResult?.setOnClickListener {
            val baseCurrency = baseCurrencyValueEditText?.text?.toString()?.toDoubleOrNull()
            textViewResult?.text = viewModel.roundValue(
                viewModel.convertValue(
                    baseCurrency, value,
                    resources.getString(R.string.currency_converter_default_value).toDouble()
                )
            )
        }
        bottomSheetDialog.show()
        if (bottomSheetDialog.behavior.isHideable) {
            baseCurrencyValueEditText?.text?.clear()
            textViewResult?.text = null
        }
    }

    private fun getCurrenciesList() {
        lifecycleScope.launch {
            viewModel.getData().observe(this@MainActivity) {
                adapter.submitData(lifecycle, it)
            }
        }
    }
}


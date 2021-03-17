package ws.worldshine.exchangerates.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ws.worldshine.exchangerates.R
import ws.worldshine.exchangerates.databinding.CurrencyItemBinding
import ws.worldshine.exchangerates.entities.CurrencyItem

class CurrencyAdapterViewHolder(private val binding: CurrencyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(currencyItem: CurrencyItem) {
        binding.apply {
            tvValueId.text = currencyItem.iD
            tvValueNumCode.text = currencyItem.name
            tvValueCharCode.text = currencyItem.charCode
            tvValueNominal.text = currencyItem.nominal.toString()
            tvValueFullName.text = currencyItem.name
            tvValueValue.text = currencyItem.value.toString()
            tvValuePrevious.text = currencyItem.previous.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): CurrencyAdapterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.currency_item, parent, false)
            val binding = CurrencyItemBinding.bind(view)
            return CurrencyAdapterViewHolder(binding)
        }
    }
}

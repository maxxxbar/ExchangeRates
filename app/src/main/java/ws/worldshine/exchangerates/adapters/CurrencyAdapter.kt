package ws.worldshine.exchangerates.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ws.worldshine.exchangerates.entities.CurrencyItem
import ws.worldshine.exchangerates.viewholders.CurrencyAdapterViewHolder

class CurrencyAdapter : PagingDataAdapter<CurrencyItem, CurrencyAdapterViewHolder>(COMPARATOR) {
    private var onCLickListener: OnClickListener? = null

    fun setOnClickListener(l: (value: Double) -> Unit) {
        this.onCLickListener = OnClickListener { value -> l(value) }
    }

    private companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CurrencyItem>() {
            override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
                return oldItem.iD == newItem.iD
            }

            override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: CurrencyAdapterViewHolder, position: Int) {
        val currencyItem = getItem(position)
        if (currencyItem?.value != null) {
            holder.bind(currencyItem)
            holder.itemView.setOnClickListener {
                onCLickListener?.onCLickListener(currencyItem.value)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAdapterViewHolder {
        return CurrencyAdapterViewHolder.create(parent)
    }

}

fun interface OnClickListener {
    fun onCLickListener(value: Double)
}
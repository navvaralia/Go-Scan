package com.myscan.goscan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myscan.goscan.data.item_class.TransactionHistoryItem
import com.myscan.goscan.databinding.GroceriesHistoryItemBinding
import java.text.NumberFormat
import java.util.*

class TransactionHistoryAdapter :
    ListAdapter<TransactionHistoryItem, TransactionHistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val historyBinding =
            GroceriesHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(historyBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = getItem(position)
        holder.bind(historyItem)
    }

    inner class HistoryViewHolder(private val binding: GroceriesHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historyItem: TransactionHistoryItem) {
            val formattingCurrency =
                NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                    .format(historyItem.groceriesPrice)
            binding.tvProductName.text = historyItem.groceriesName
            binding.tvProductAmount.text = historyItem.groceriesTotal.toString()
            binding.tvProductPrice.text = formattingCurrency.toString()
            binding.tvProductDate.text = historyItem.dateData
        }
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<TransactionHistoryItem> =
            object : DiffUtil.ItemCallback<TransactionHistoryItem>() {
                override fun areItemsTheSame(
                    oldItem: TransactionHistoryItem,
                    newItem: TransactionHistoryItem
                ): Boolean {
                    return oldItem.groceriesName == newItem.groceriesName
                }

                override fun areContentsTheSame(
                    oldItem: TransactionHistoryItem,
                    newItem: TransactionHistoryItem
                ): Boolean {
                    return oldItem == newItem
                }
            }

    }
}


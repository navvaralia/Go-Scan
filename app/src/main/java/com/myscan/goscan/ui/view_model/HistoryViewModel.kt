package com.myscan.goscan.ui.view_model

import androidx.lifecycle.ViewModel
import com.myscan.goscan.data.TransactionHistoryRepository
import com.myscan.goscan.data.item_class.TransactionHistoryItem

class HistoryViewModel : ViewModel() {

    private val historyRepository = TransactionHistoryRepository()

    fun getTransactionHistory(callback: (List<TransactionHistoryItem>) -> Unit) {
        historyRepository.getTransaction(callback)
    }

    fun deleteAllTransactionHistory(
        collectionName: String,
        success: () -> Unit,
        failed: (String) -> Unit
    ) {
        historyRepository.deleteTransaction(collectionName, success, failed)
    }
}
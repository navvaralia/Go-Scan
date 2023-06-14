package com.myscan.goscan.ui.view_model

import androidx.lifecycle.ViewModel
import com.myscan.goscan.data.GroceriesRepository
import com.myscan.goscan.data.item_class.GroceriesItem

class GroceriesViewModel : ViewModel() {

    private val groceriesRepository = GroceriesRepository()

    fun getGroceriesItems(callback: (List<GroceriesItem>, Double) -> Unit) {
        groceriesRepository.getGroceriesItem(callback)
    }

    fun addGroceriesItem(
        groceriesItem: GroceriesItem, success: () -> Unit,
        failed: (String) -> Unit
    ) {
        groceriesRepository.addGroceriesItem(groceriesItem, success, failed)
    }

    fun transferCopyCollection(
        senderCollection: String,
        receiverCollection: String,
        dateData: String,
        success: () -> Unit,
        failed: (String) -> Unit
    ) {
        groceriesRepository.transferCollection(
            senderCollection,
            receiverCollection,
            dateData,
            success,
            failed
        )
    }

    fun deleteAllGroceries(
        collectionName: String,
    ) {
        groceriesRepository.deleteGroceriesItem(collectionName)
    }
}
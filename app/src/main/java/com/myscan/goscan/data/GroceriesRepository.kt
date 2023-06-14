package com.myscan.goscan.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.myscan.goscan.data.item_class.GroceriesItem

class GroceriesRepository {

    private val fbFirestore = FirebaseFirestore.getInstance()
    private val collections = fbFirestore.collection("product")

    fun addGroceriesItem(
        groceriesItem: GroceriesItem, success: () -> Unit,
        failed: (String) -> Unit
    ) {
        collections.add(groceriesItem)
            .addOnSuccessListener {
                success()
            }
            .addOnFailureListener { exceptions ->
                failed(exceptions.message ?: "Error Occurred")
            }
    }

    fun getGroceriesItem(
        callback: (List<GroceriesItem>, Double) -> Unit
    ): ListenerRegistration {
        return collections
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    val items = snapshots.documents.mapNotNull { docs ->
                        docs.toObject(GroceriesItem::class.java)
                    }
                    val totalCount = totalPriceCount(items)
                    callback(items, totalCount)
                }
            }
    }


    fun transferCollection(
        senderCollection: String,
        receiverCollection: String,
        dateData: String,
        success: () -> Unit,
        failed: (String) -> Unit
    ) {

        fbFirestore.collection(senderCollection)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (collections in querySnapshot) {
                    val collectionsData = collections.data.toMutableMap()
                    collectionsData[DATE_DATA] = dateData

                    fbFirestore.collection(receiverCollection)
                        .document(collections.id)
                        .set(collectionsData)
                        .addOnSuccessListener {
                            success()
                        }
                        .addOnFailureListener { exceptions ->
                            failed(exceptions.message ?: "Error Occurred")
                        }
                }
            }
            .addOnFailureListener { exceptions ->
                failed(exceptions.message ?: "Error Occurred")
            }
    }

    fun deleteGroceriesItem(
        collectionName: String,
    ) {
        fbFirestore.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshots ->
                for (data in querySnapshots) {
                    data.reference.delete()
                }
            }
    }

     private fun totalPriceCount(groceriesItems: List<GroceriesItem>): Double {
        var totalCount = 0.0
        for (total in groceriesItems) {
            totalCount += total.groceriesPrice
        }
        return totalCount
    }

    companion object {
        const val DATE_DATA = "dateData"
    }
}
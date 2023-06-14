package com.myscan.goscan.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.myscan.goscan.data.item_class.TransactionHistoryItem

class TransactionHistoryRepository {
    private val fbFirestore = FirebaseFirestore.getInstance()
    private val collections = fbFirestore.collection("historytransaction")

    fun getTransaction(
        callback: (List<TransactionHistoryItem>) -> Unit
    ): ListenerRegistration {
        return collections
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    val items = snapshots.documents.mapNotNull { docs ->
                        docs.toObject(TransactionHistoryItem::class.java)
                    }
                    callback(items)
                }
            }
    }

    fun deleteTransaction(
        collectionName: String,
        success: () -> Unit,
        failed: (String) -> Unit
    ) {
        fbFirestore.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshots ->
                for (data in querySnapshots) {
                    data.reference.delete()
                    success()
                }
            }
            .addOnFailureListener { exceptions ->
                failed(exceptions.message ?: "Error Occurred")
            }
    }
}
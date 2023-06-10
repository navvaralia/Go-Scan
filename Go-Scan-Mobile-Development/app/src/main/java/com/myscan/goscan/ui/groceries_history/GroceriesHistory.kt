package com.myscan.goscan.ui.groceries_history

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myscan.goscan.R
import com.myscan.goscan.databinding.ActivityGroceriesHistoryBinding
import com.myscan.goscan.ui.adapter.TransactionHistoryAdapter
import com.myscan.goscan.ui.view_model.HistoryViewModel

class GroceriesHistory : AppCompatActivity() {
    private lateinit var binding: ActivityGroceriesHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var rvAdapter: TransactionHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceriesHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = TransactionHistoryAdapter()
        binding.rvTransactionHistory.layoutManager = LinearLayoutManager(this)
        binding.rvTransactionHistory.adapter = rvAdapter

        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        historyViewModel.getTransactionHistory { historyItems ->
            rvAdapter.submitList(historyItems)
        }

        binding.fabDeleteHistory.setOnClickListener {
            val showAlertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_history_title))
                .setMessage(getString(R.string.delete_confirmation))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->

                    historyViewModel.deleteAllTransactionHistory(
                        "historytransaction",
                        success = {
                            Toast.makeText(
                                this,
                                getString(R.string.success_delete_history),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        failed = {
                            Toast.makeText(
                                this,
                                getString(R.string.failed_deleting_history),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
                .setNegativeButton(getString(R.string.no)) { _, _ -> }
                .create()
            showAlertDialog.setOnDismissListener {

            }
            showAlertDialog.show()

        }
    }
}
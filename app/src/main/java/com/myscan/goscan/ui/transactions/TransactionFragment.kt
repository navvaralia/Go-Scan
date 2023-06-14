package com.myscan.goscan.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.myscan.goscan.R
import com.myscan.goscan.databinding.FragmentTransactionBinding
import com.myscan.goscan.ui.adapter.TransactionHistoryAdapter
import com.myscan.goscan.ui.view_model.TransactionViewModel

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private lateinit var rvAdapter: TransactionHistoryAdapter
    private val transactionViewModel: TransactionViewModel by viewModels()

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = TransactionHistoryAdapter()
        binding?.rvTransactionHistory?.layoutManager = LinearLayoutManager(activity)
        binding?.rvTransactionHistory?.adapter = rvAdapter

        transactionViewModel.getTransactionHistory { historyItems ->
            rvAdapter.submitList(historyItems)
        }

        binding?.fabDeleteHistory?.setOnClickListener {
            val showAlertDialog = activity?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle(getString(R.string.delete_history_title))
                    .setMessage(getString(R.string.delete_confirmation))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->

                        transactionViewModel.deleteAllTransactionHistory(
                            "historytransaction",
                            success = {
                                Toast.makeText(
                                    activity,
                                    getString(R.string.success_delete_history),
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            failed = {
                                Toast.makeText(
                                    activity,
                                    getString(R.string.failed_deleting_history),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ -> }
                    .create()
            }
            showAlertDialog?.setOnDismissListener {

            }
            showAlertDialog?.show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
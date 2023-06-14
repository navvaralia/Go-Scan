package com.myscan.goscan.ui.groceries

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myscan.goscan.R
import com.myscan.goscan.databinding.FragmentGroceriesBinding
import com.myscan.goscan.databinding.PaymentPopUpBinding
import com.myscan.goscan.ui.adapter.GroceriesAdapter
import com.myscan.goscan.ui.view_model.GroceriesViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class GroceriesFragment : Fragment() {

    private var _binding: FragmentGroceriesBinding? = null
    private val binding get() = _binding

    private var totalCountPopUp: Double = 0.0
    private lateinit var rvAdapter: GroceriesAdapter
    private val groceriesViewModel: GroceriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGroceriesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvAdapter = GroceriesAdapter()
        binding?.rvGroceries?.layoutManager = LinearLayoutManager(context)
        binding?.rvGroceries?.adapter = rvAdapter

        groceriesViewModel.getGroceriesItems { groceriesItems, totalCounts ->
            rvAdapter.submitList(groceriesItems)
            totalCountText(totalCounts)
            totalCountPopUp = totalCounts
        }

        binding?.btnTotalPrice?.setOnClickListener {
            val binding = PaymentPopUpBinding.inflate(layoutInflater)
            val totalForPaying = binding.tvTotalCount
            val totalChange = binding.tvTotalChange

            val formattingCurrency =
                NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            totalForPaying.text = formattingCurrency.format(totalCountPopUp)

            val inputtedPaying = binding.tiPaying

            inputtedPaying.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val inputAmount = s.toString().toDoubleOrNull()
                    if (inputAmount != null) {
                        val changeTotal = inputAmount - totalCountPopUp

                        totalChange.text = formattingCurrency.format(changeTotal)
                    } else {
                        totalChange.text = formattingCurrency.format(0)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(binding.root)
                .setTitle(context?.getString(R.string.payment))
                .setMessage(context?.getString(R.string.payment_confirmation))
                .setPositiveButton(context?.getString(R.string.yes)) { dialogs, _ ->

                    val formatDate =
                        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
                    groceriesViewModel.transferCopyCollection(
                        "product",
                        "historytransaction",
                        formatDate,
                        success = {
                            Toast.makeText(
                                context,
                                context?.getString(R.string.success_adding_history),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        failed = {
                            Toast.makeText(
                                context,
                                context?.getString(R.string.failed_adding_history),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )

                    groceriesViewModel.deleteAllGroceries("product")
                    dialogs.dismiss()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
            dialogBuilder.show().create()
        }


        binding?.fabAddGroceriesList?.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_navigation_groceries_to_addGroceriesListActivity3)
        }
    }

    private fun totalCountText(totalCount: Double) {
        val formattingCurrency =
            NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(totalCount)
        val totalCountText = context?.getString(R.string.total_count, formattingCurrency)
        binding?.btnTotalPrice?.text = totalCountText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
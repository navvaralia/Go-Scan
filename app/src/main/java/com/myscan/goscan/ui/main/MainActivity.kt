package com.myscan.goscan.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myscan.goscan.R
import com.myscan.goscan.databinding.ActivityMainBinding
import com.myscan.goscan.ui.adapter.GroceriesAdapter
import com.myscan.goscan.ui.add_item.AddGroceriesListActivity
import com.myscan.goscan.ui.groceries_history.GroceriesHistory
import com.myscan.goscan.ui.view_model.GroceriesViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import androidx.datastore.preferences.core.Preferences
import com.myscan.goscan.databinding.PaymentPopUpBinding
import com.myscan.goscan.utils.ModeSettingPreferences
import com.myscan.goscan.utils.ModeSettingViewModel
import com.myscan.goscan.utils.ModeSettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var groceriesViewModel: GroceriesViewModel
    private lateinit var rvAdapter: GroceriesAdapter

    private var totalCountPopUp: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)

        rvAdapter = GroceriesAdapter()
        binding.rvGroceries.layoutManager = LinearLayoutManager(this)
        binding.rvGroceries.adapter = rvAdapter

        groceriesViewModel = ViewModelProvider(this)[GroceriesViewModel::class.java]

        groceriesViewModel.getGroceriesItems { groceriesItems, totalCounts ->
            rvAdapter.submitList(groceriesItems)
            totalCountText(totalCounts)
            totalCountPopUp = totalCounts
        }

        binding.btnTotalPrice.setOnClickListener {
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

            val dialogBuilder = android.app.AlertDialog.Builder(this)
                .setView(binding.root)
                .setTitle(getString(R.string.payment))
                .setMessage(getString(R.string.payment_confirmation))
                .setPositiveButton(getString(R.string.yes)) { dialogs, _ ->

                    val formatDate =
                        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
                    groceriesViewModel.transferCopyCollection(
                        "product",
                        "historytransaction",
                        formatDate,
                        success = {
                            Toast.makeText(
                                this,
                                getString(R.string.success_adding_history),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        failed = {
                            Toast.makeText(
                                this,
                                getString(R.string.failed_adding_history),
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

        binding.fabAddGroceriesList.setOnClickListener {
            startActivity(Intent(this, AddGroceriesListActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val themeIcon = menu.findItem(R.id.appMode)
        val modePref = ModeSettingPreferences.getInstance(dataStore)
        val modeSettingViewModel = ViewModelProvider(
            this,
            ModeSettingViewModelFactory(modePref)
        )[ModeSettingViewModel::class.java]

        modeSettingViewModel.getThemeApp().observe(this) { activateMode: Boolean ->
            if (activateMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeIcon.isChecked = true
                themeIcon.setIcon(R.drawable.ic_light_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeIcon.isChecked = false
                themeIcon.setIcon(R.drawable.ic_dark_mode)
            }
        }

        themeIcon.setOnMenuItemClickListener {
            if (!it.isChecked) {
                it.isChecked = true
                modeSettingViewModel.applyingTheme(it.isChecked)
            } else {
                it.isChecked = false
                modeSettingViewModel.applyingTheme(it.isChecked)
            }
            true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.groceriesHistory -> {
                startActivity(Intent(this, GroceriesHistory::class.java))
            }
            R.id.appMode -> {
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun totalCountText(totalCount: Double) {
        val formattingCurrency =
            NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(totalCount)
        val totalCountText = getString(R.string.total_count, formattingCurrency)
        binding.btnTotalPrice.text = totalCountText
    }

}
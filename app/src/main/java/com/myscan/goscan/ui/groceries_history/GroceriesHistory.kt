package com.myscan.goscan.ui.groceries_history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myscan.goscan.databinding.ActivityGroceriesHistoryBinding

class GroceriesHistory : AppCompatActivity() {
    private lateinit var binding: ActivityGroceriesHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceriesHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
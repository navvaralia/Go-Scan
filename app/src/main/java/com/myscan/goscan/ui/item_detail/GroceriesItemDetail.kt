package com.myscan.goscan.ui.item_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myscan.goscan.databinding.ActivityGroceriesItemDetailBinding

class GroceriesItemDetail : AppCompatActivity() {
    private lateinit var binding: ActivityGroceriesItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceriesItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
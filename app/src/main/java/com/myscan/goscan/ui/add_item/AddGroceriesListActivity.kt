package com.myscan.goscan.ui.add_item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myscan.goscan.databinding.ActivityAddGroceriesListBinding

class AddGroceriesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGroceriesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroceriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
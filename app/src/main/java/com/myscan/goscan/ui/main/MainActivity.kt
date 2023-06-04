package com.myscan.goscan.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.myscan.goscan.R
import com.myscan.goscan.databinding.ActivityMainBinding
import com.myscan.goscan.ui.add_item.AddGroceriesListActivity
import com.myscan.goscan.ui.groceries_history.GroceriesHistory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabAddList.setOnClickListener {
            startActivity(Intent(this, AddGroceriesListActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
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
}
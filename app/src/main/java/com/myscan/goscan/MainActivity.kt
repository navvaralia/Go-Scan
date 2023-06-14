package com.myscan.goscan

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.myscan.goscan.databinding.ActivityMainBinding
import com.myscan.goscan.utils.ModeSettingPreferences
import com.myscan.goscan.utils.ModeSettingViewModel
import com.myscan.goscan.utils.ModeSettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_groceries, R.id.navigation_transaction
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
            R.id.appMode -> {}
        }
        return super.onOptionsItemSelected(item)
    }

}
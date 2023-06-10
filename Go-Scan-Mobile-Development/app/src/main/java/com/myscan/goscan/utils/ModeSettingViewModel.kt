package com.myscan.goscan.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ModeSettingViewModel(private val preferences: ModeSettingPreferences) : ViewModel() {
    fun getThemeApp(): LiveData<Boolean> {
        return preferences.fetchTheme().asLiveData()
    }

    fun applyingTheme(activateMode: Boolean) {
        viewModelScope.launch {
            preferences.setTheme(activateMode)
        }
    }
}
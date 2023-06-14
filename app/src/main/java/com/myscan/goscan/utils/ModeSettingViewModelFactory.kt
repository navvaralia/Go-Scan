package com.myscan.goscan.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModeSettingViewModelFactory(private val preferences: ModeSettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ModeSettingViewModel::class.java) -> {
                ModeSettingViewModel(preferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModelClass: " + modelClass.name)
        }
    }
}
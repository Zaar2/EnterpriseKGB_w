package com.zaar.meatkgb2_w.viewModel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaar.meatkgb2_w.viewModel.vm.SettingVM

class SettingFactory(
    val context: Context
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingVM(context) as T
    }
}
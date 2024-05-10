package com.zaar.meatKGB_w.viewModel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaar.meatKGB_w.viewModel.vm.IdentificationVM

class IdentificationFactory(
   val context: Context,
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IdentificationVM(context) as T
    }
}
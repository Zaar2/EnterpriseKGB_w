package com.zaar2.meatKGB_w.viewModel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaar2.meatKGB_w.viewModel.vm.WorkspaceVM

class WorkspaceFactory(
    val context: Context
):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkspaceVM(context) as T
    }
}
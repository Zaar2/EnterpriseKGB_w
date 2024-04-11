package com.zaar.meatkgb2_w.model.useCase

import android.content.Context
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl

class GetIdEnterpriseUseCase(
    private val myContext: Context?,
) {
    suspend fun execute(): String? =
        myContext?.let { LocalDBRepositoryImpl(it).getIdEnterprise() }
}
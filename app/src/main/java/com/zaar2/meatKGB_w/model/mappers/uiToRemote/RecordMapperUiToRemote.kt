package com.zaar2.meatKGB_w.model.mappers.uiToRemote

import android.content.Context
import com.zaar2.meatKGB_w.model.entityUi.RecordUi
import com.zaar2.meatKGB_w.model.remote.entityRemote.RecordRemote
import com.zaar2.meatKGB_w.model.repository.LocalDBRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecordMapperUiToRemote(
    private val context: Context,
) {

    suspend fun executeSuspend(
        recordUi: RecordUi,
    ): RecordRemote {
        return withContext(Dispatchers.IO) {
            async {
                val workerId = LocalDBRepositoryImpl(context).getUserIdByLogin(recordUi.userLogin)
                val productId =
                    LocalDBRepositoryImpl(context).getProductIdByName(recordUi.productName)
                RecordRemote(
                    id = -1L,
                    id_worker = workerId,
                    id_product = productId,
                    date_produced = recordUi.dateProduced,
                    time_produced = recordUi.timeProduced,
                    count = recordUi.count,
                    enterpriseId = recordUi.enterpriseId,
                    id_shop = recordUi.shopId,
                )
            }.await()
        }
    }
}
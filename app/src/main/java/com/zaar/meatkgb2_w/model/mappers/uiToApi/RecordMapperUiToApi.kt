package com.zaar.meatkgb2_w.model.mappers.uiToApi

import android.content.Context
import com.zaar.meatkgb2_w.model.entityUi.RecordUi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.RecordApi
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecordMapperUiToApi(
    private val context: Context,
) {

    suspend fun executeSuspend(
        recordUi: RecordUi,
    ): RecordApi {
        return withContext(Dispatchers.IO) {
            async {
                val workerId = LocalDBRepositoryImpl(context).getUserIdByLogin(recordUi.userLogin)
                val productId =
                    LocalDBRepositoryImpl(context).getProductIdByName(recordUi.productName)
                RecordApi(
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
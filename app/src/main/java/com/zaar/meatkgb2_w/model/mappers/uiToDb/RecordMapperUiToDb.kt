package com.zaar.meatkgb2_w.model.mappers.uiToDb

import android.content.Context
import com.zaar.meatkgb2_w.data.entity.RecordUi
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.RecordDb
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordMapperUiToDb(
    private val recordUi: RecordUi,
    private val context: Context,
) {
   suspend fun executeSuspend(): RecordDb {
       return withContext(Dispatchers.IO) {
           async {
               val workerId = LocalDBRepositoryImpl(context).getUserIdByLogin(recordUi.userLogin)
               val productId =
                   LocalDBRepositoryImpl(context).getProductIdByName(recordUi.productName)
               RecordDb(
                   id = -1L,
                   idWorker = workerId,
                   idProduct = productId,
                   dateProduced = recordUi.dateProduced,
                   timeProduced = recordUi.timeProduced,
                   count = recordUi.count
               )
           }.await()
       }
    }
}
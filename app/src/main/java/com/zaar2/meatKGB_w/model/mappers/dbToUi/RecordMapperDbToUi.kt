package com.zaar2.meatKGB_w.model.mappers.dbToUi

import android.content.Context
import com.zaar2.meatKGB_w.model.entityUi.RecordUi
import com.zaar2.meatKGB_w.model.local.api_room.entityDb.RecordDb
import com.zaar2.meatKGB_w.model.repository.LocalDBRepositoryImpl
import com.zaar2.meatKGB_w.utilities.view.UtilitiesTextFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecordMapperDbToUi(
    private val context: Context
) {
    suspend fun executeSuspend(
        recordsDb: List<RecordDb>
    ): List<RecordUi> {
        return withContext(Dispatchers.IO) {
            async {
                val listUi = mutableListOf<RecordUi>()
                recordsDb.forEach { recordDb ->
                    listUi.add(
                        RecordUi(
                            id = recordDb.id,
                            dateProduced = UtilitiesTextFormat()
                                .convertDateServToLocal(recordDb.dateProduced) ?: "",
                            productName = LocalDBRepositoryImpl(context)
                                .getProductNameById(recordDb.idProduct),
                            timeProduced = recordDb.timeProduced,
                            count = recordDb.count,
                            me = LocalDBRepositoryImpl(context)
                                .getMeByProductId(recordDb.idProduct)
                        )
                    )
                }
                listUi
            }.await()
        }
    }
}
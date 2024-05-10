package com.zaar.meatKGB_w.model.mappers.apiToDb

import com.zaar.meatKGB_w.model.local.api_room.entityDb.RecordDb
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.RecordApi

class RecordMapperApiToDb {
    fun execute(
        recordApi: RecordApi
    ): RecordDb {
        return recordApi.run {
            RecordDb(
                id = id,
                idWorker = id_worker,
                dateProduced = date_produced,
                idProduct = id_product,
                timeProduced = time_produced,
                count = count
            )
        }
    }

    fun execute(
        recordsApi: List<RecordApi>
    ): List<RecordDb> {
        val listDb = mutableListOf<RecordDb>()
        recordsApi.forEach {
            listDb.add(
                RecordDb(
                    id = it.id,
                    idProduct = it.id_product,
                    idWorker = it.id_worker,
                    dateProduced = it.date_produced,
                    timeProduced = it.time_produced,
                    count = it.count
                )
            )
        }
        return listDb
    }
}
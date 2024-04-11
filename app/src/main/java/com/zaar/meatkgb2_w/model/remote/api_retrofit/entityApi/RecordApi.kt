package com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class RecordApi(
    var id: Long,
    var idWorker: Long = -1,
    var dateProduced: String = "non",
    var idProduct: Long = -1,
    var timeProduced: Byte = -1,
    var count: Float = -1f,
)

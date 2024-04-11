package com.zaar.meatkgb2_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "id_worker") var idWorker: Long = -1,
    @ColumnInfo(name = "date_produced") var dateProduced: String = "non",
    @ColumnInfo(name = "id_product") var idProduct: Long = -1,
    @ColumnInfo(name = "time_produced") var timeProduced: Byte = -1,
    @ColumnInfo(name = "count") var count: Float = -1f,
)
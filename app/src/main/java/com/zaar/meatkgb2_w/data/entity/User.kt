package com.zaar.meatkgb2_w.data.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class User(
    var id: Long,
    var nameFull: String,
    var nameShort: String,
    var appointment: String,
    var idWorkshop: Long,
)

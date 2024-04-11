package com.zaar.meatkgb2_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "nameFull") var nameFull: String,
    @ColumnInfo(name = "nameShort") var nameShort: String,
    @ColumnInfo(name = "appointment") var appointment: String,
    @ColumnInfo(name = "idWorkshop") var idWorkshop: Long,
)

package com.zaar.meatKGB_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "nameFull") var nameFull: String,
    @ColumnInfo(name = "nameShort") var nameShort: String,
    @ColumnInfo(name = "appointment") var appointment: String,
    @ColumnInfo(name = "idWorkshop") var idWorkshop: Long,
    @ColumnInfo(name = "usr_login") val usrLogin: String,
    @ColumnInfo(name = "id_one_more_workshop") val idOneMoreWorkshop: Long,
)

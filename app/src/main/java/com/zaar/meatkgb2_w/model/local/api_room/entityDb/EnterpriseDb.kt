package com.zaar.meatkgb2_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enterprise")
data class EnterpriseDb(
    @PrimaryKey(autoGenerate = false) var id:String,
    @ColumnInfo(name = "nameEnterprise") var nameEnterprise: String,
    @ColumnInfo(name = "eMail") var eMail: String,
)

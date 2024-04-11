package com.zaar.meatkgb2_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ShopDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "nameShop") var nameShop: String
)

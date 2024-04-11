package com.zaar.meatkgb2_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "nameProduct") var nameProduct: String,
    @ColumnInfo(name = "idShop") var idShop: Long,
    @ColumnInfo(name = "me") var me: String,
    @ColumnInfo(name = "accuracy") var accuracy: Int,
)

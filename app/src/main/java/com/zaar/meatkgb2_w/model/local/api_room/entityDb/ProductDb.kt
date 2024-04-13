package com.zaar.meatkgb2_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "product_name") var nameProduct: String,
    @ColumnInfo(name = "id_workshop") var idShop: Long,
    @ColumnInfo(name = "me") var me: String,
    @ColumnInfo(name = "accuracy") var accuracy: Int,
    @ColumnInfo(name = "id_status") var idStatus: Int
)

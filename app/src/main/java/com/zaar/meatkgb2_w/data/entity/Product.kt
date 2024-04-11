package com.zaar.meatkgb2_w.data.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Product(
    var id: Long,
    var nameProduct: String,
    var idShop: Long,
    var me: String,
    var accuracy: Int,
)

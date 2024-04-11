package com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ProductApi(
    var id: Long,
    var nameProduct: String,
    var idShop: Long,
    var me: String,
    var accuracy: Int,
)

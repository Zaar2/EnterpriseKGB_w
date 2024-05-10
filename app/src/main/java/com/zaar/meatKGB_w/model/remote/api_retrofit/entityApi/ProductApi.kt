package com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi

data class ProductApi(
    var id: Long,
    var product_name: String,
    var id_workshop: Long,
    var me: String,
    var accuracy: Int,
    val id_status: Int
)

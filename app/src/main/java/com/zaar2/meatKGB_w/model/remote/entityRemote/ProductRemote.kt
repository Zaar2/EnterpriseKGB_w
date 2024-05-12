package com.zaar2.meatKGB_w.model.remote.entityRemote

data class ProductRemote(
    var id: Long,
    var product_name: String,
    var id_workshop: Long,
    var me: String,
    var accuracy: Int,
    val id_status: Int
)

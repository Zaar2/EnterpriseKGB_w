package com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi

data class ShopApi(
    var id: Long,
    var name: String,
    var short_name: String,
    val nonManufacture_isChecked: String,
    val id_type_role: Int,
    val id_status: Int,
)

package com.zaar2.meatKGB_w.model.remote.entityRemote

data class ShopRemote(
    var id: Long,
    var name: String,
    var short_name: String,
    val nonManufacture_isChecked: String,
    val id_type_role: Int,
    val id_status: Int,
)

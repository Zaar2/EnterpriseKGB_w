package com.zaar.meatkgb2_w.data.entity

data class RecordUi(
    var id: Long=-1L,
    var userLogin: String = "",
    var dateProduced: String = "",
    var productName: String = "",
    var timeProduced: Byte = -1,
    var count: Float = -1f,
    var enterpriseId: String = "",
    var shopId: Long = -1L,
    var me: String = "",
)

package com.zaar2.meatKGB_w.model.remote.entityRemote

data class RecordRemote(
    var id: Long,
    var id_worker: Long = -1L,
    var date_produced: String = "non",
    var id_product: Long = -1L,
    var time_produced: Byte = -1,
    var count: Float = -1f,
    var enterpriseId: String = "non",
    var id_shop: Long = -1L,
) {
    fun isReady(): Boolean =
        (id_worker >= 0
                && (date_produced.isNotEmpty() && date_produced != "non")
                && id_product >= 0
                && time_produced in 0..23
                && count >= 0
                && (enterpriseId.isNotEmpty() && enterpriseId != "non")
                && id_shop >= 0
                )
}

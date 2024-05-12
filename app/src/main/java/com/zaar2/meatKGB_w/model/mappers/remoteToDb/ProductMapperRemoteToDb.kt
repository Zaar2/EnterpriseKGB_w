package com.zaar2.meatKGB_w.model.mappers.remoteToDb

import com.zaar2.meatKGB_w.model.local.api_room.entityDb.ProductDb
import com.zaar2.meatKGB_w.model.remote.entityRemote.ProductRemote

class ProductMapperRemoteToDb(
    private val productRemote: ProductRemote
) {
    fun execute():ProductDb=
        ProductDb(
            id = productRemote.id,
            nameProduct = productRemote.product_name,
            idShop = productRemote.id_workshop,
            me = productRemote.me,
            accuracy = productRemote.accuracy,
            idStatus = productRemote.id_status
        )
}
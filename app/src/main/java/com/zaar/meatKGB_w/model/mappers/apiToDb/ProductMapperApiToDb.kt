package com.zaar.meatKGB_w.model.mappers.apiToDb

import com.zaar.meatKGB_w.model.local.api_room.entityDb.ProductDb
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.ProductApi

class ProductMapperApiToDb(
    private val productApi: ProductApi
) {
    fun execute():ProductDb=
        ProductDb(
            id = productApi.id,
            nameProduct = productApi.product_name,
            idShop = productApi.id_workshop,
            me = productApi.me,
            accuracy = productApi.accuracy,
            idStatus = productApi.id_status
        )
}
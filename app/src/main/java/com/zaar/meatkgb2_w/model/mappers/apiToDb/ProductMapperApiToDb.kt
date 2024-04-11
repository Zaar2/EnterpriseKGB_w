package com.zaar.meatkgb2_w.model.mappers.apiToDb

import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ProductApi

class ProductMapperApiToDb(
    private val productApi: ProductApi
) {
    fun execute():ProductDb=
        ProductDb(
            id = productApi.id,
            nameProduct = productApi.nameProduct,
            idShop = productApi.idShop,
            me = productApi.me,
            accuracy = productApi.accuracy
        )
}
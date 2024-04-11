package com.zaar.meatkgb2_w.model.mappers.apiToDb

import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ShopDb
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ShopApi

class ShopMapperApiToDb(
    private val shopApi: ShopApi,
) {
    fun execute(): ShopDb =
        ShopDb(
            id = shopApi.id,
            nameShop = shopApi.nameShop
        )
}
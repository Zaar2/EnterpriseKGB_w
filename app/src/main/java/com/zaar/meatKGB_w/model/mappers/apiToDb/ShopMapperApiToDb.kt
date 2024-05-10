package com.zaar.meatKGB_w.model.mappers.apiToDb

import com.zaar.meatKGB_w.model.local.api_room.entityDb.ShopDb
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.ShopApi

class ShopMapperApiToDb(
    private val shopApi: ShopApi,
) {
    fun execute(): ShopDb =
        ShopDb(
            id = shopApi.id,
            nameShop = shopApi.name,
            nonManufactureIsChecked = shopApi.nonManufacture_isChecked,
            idTypeRole = shopApi.id_type_role,
            idStatus = shopApi.id_status,
            shortName = shopApi.short_name,
        )
}
package com.zaar2.meatKGB_w.model.mappers.remoteToDb

import com.zaar2.meatKGB_w.model.local.api_room.entityDb.ShopDb
import com.zaar2.meatKGB_w.model.remote.entityRemote.ShopRemote

class ShopMapperRemoteToDb(
    private val shopRemote: ShopRemote,
) {
    fun execute(): ShopDb =
        ShopDb(
            id = shopRemote.id,
            nameShop = shopRemote.name,
            nonManufactureIsChecked = shopRemote.nonManufacture_isChecked,
            idTypeRole = shopRemote.id_type_role,
            idStatus = shopRemote.id_status,
            shortName = shopRemote.short_name,
        )
}
package com.zaar.meatkgb2_w.model.repository

import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ShopDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb

interface LocalDBRepository {
    fun getCryptoIdEnterprise(): String?
    suspend fun getIdEnterprise(): String?
    suspend fun userInsertWithReplace(userDb: UserDb): Long
    suspend fun shopInsertWithReplace(shopDb: ShopDb): Long
    suspend fun productInsertWithReplace(productsDb: List<ProductDb>): LongArray
    suspend fun getUserWorkshop(): Long
}
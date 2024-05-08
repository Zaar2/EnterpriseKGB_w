package com.zaar.meatkgb2_w.model.repository

import com.zaar.meatkgb2_w.data.UserDescription
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.RecordDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ShopDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb

interface LocalDBRepository {
    fun getCryptoIdEnterprise(): String?
    suspend fun getIdEnterprise(): String?
    suspend fun userInsertWithReplace(userDb: UserDb): Long
    suspend fun shopInsertWithReplace(shopDb: ShopDb): Long
    suspend fun productsInsertWithReplace(productsDb: List<ProductDb>): LongArray
    suspend fun productsInsert(productsDb: List<ProductDb>): LongArray
    suspend fun getIdWorkshop(): Long
    suspend fun getIdMoreWorkshop(): Long
    suspend fun getShopIdByProductName(productName: String): Long
    suspend fun getAccuracyByProductName(productName: String): Int
    suspend fun getUserIdByLogin(login: String): Long

    //    fun getUserId(login: String): Long
    suspend fun getProductIdByName(name: String): Long
    suspend fun getProductNameById(id: Long): String
    suspend fun getIdRoleByShop(): Long
    suspend fun getUserDescription(): UserDescription?
    suspend fun getProduct(): List<String>?
    suspend fun getMeByProduct(productName: String): String
    suspend fun getMeByProductId(id: Long): String
//    suspend fun setRecord(recordDb: RecordDb): Long
    suspend fun setRecord(recordsDb: List<RecordDb>): LongArray?
}
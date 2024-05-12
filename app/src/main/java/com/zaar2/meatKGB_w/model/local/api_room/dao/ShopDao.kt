package com.zaar2.meatKGB_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar2.meatKGB_w.model.local.api_room.entityDb.ShopDb

@Dao
interface ShopDao: BaseDao<ShopDb> {
    @Query("delete from shop")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(enterprise: ShopDb): Long

    @Transaction
    suspend fun insertWithReplace(shop: ShopDb): Long {
        deleteAll()
        return insert(shop)
    }

    @Query("select id_type_role from shop limit 1")
    suspend fun getRoleByShop(): Long

}
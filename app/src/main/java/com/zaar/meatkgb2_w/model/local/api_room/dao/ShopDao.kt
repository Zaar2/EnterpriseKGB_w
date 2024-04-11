package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ShopDb

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
}
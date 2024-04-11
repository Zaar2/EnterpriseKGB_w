package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb

@Dao
interface ProductDao: BaseDao<ProductDb> {

    @Query("delete from products")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(enterprise: ProductDb): LongArray

    @Transaction
    suspend fun insertWithReplace(product: List<ProductDb>): LongArray {
        deleteAll()
        return insert(product)
    }
}
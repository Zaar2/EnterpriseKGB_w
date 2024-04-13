package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb
import kotlinx.coroutines.selects.select

@Dao
interface ProductDao: BaseDao<ProductDb> {

    @Query("delete from products")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuspend(products: List<ProductDb>): LongArray

    @Transaction
    suspend fun insertWithReplace(products: List<ProductDb>): LongArray {
        deleteAll()
        return insertSuspend(products)
    }

    @Query("select product_name from products order by product_name")
    suspend fun getProduct(): List<String>

    @Query("select me from products where product_name=:productName limit 1")
    suspend fun getMeByProduct(productName: String): String
}
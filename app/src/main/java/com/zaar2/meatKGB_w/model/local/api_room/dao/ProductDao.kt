package com.zaar2.meatKGB_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar2.meatKGB_w.model.local.api_room.entityDb.ProductDb

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

    @Query("select me from products where id=:id")
    suspend fun getMeById(id: Long): String

    @Query("select id from products where product_name=:name limit 1")
    suspend fun getIdByName(name: String): Long

    @Query("select product_name from products where id=:id limit 1")
    suspend fun getNameById(id: Long): String

    @Query("select id_workshop from products where product_name=:name limit 1")
    suspend fun getShopIdByName(name: String): Long

    @Query("select accuracy from products where product_name=:name limit 1")
    suspend fun getAccuracyByName(name: String): Int
}
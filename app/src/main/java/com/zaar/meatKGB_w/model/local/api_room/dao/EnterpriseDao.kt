package com.zaar.meatKGB_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatKGB_w.model.local.api_room.entityDb.EnterpriseDb

@Dao
interface EnterpriseDao: BaseDao<EnterpriseDb> {

    @Query("delete from enterprise")
    fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(enterprise: EnterpriseDb)

    @Transaction
    fun insertWithReplace(enterprise: EnterpriseDb) {
        deleteAll()
        insert(enterprise)
    }

    @Query("select id from Enterprise")
    fun getCryptoIdEnterprise(): List<String>

    @Query("select id from Enterprise")
    suspend fun getIdEnterprise(): List<String>
}
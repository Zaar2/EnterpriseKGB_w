package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb

@Dao
interface UsrDataDao: BaseDao<UserDb> {
    @Query("delete from userData")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDb: UserDb): Long

    @Transaction
    suspend fun insertWithReplace(usrData: UserDb): Long {
        deleteAll()
        return insert(usrData)
    }

    @Query("select idWorkshop from userData")
    suspend fun getWorkshop(): Long
}
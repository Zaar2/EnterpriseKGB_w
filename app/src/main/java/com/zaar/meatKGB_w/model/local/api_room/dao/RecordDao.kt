package com.zaar.meatKGB_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatKGB_w.model.local.api_room.entityDb.RecordDb

@Dao
interface RecordDao: BaseDao<RecordDb> {
    @Query("delete from records")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(enterprise: RecordDb)

//    @Transaction
//    fun insertWithReplace(record: RecordDb) {
//        deleteAll()
//        insert(record)
//    }

    @Transaction
   suspend fun insertWithReplace(records: List<RecordDb>): LongArray {
        deleteAll()
       return insert(records)
    }
}
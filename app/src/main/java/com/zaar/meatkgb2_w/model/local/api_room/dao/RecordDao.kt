package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_w.data.RecordShopReportOut
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.RecordDb

@Dao
interface RecordDao: BaseDao<RecordDb> {
    @Query("delete from records")
    fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(enterprise: RecordDb)

    @Transaction
    fun insertWithReplace(record: RecordDb) {
        deleteAll()
        insert(record)
    }

    @Query(
        "select" +
                " rep.id as id," +
                " rep.date_produced as date_produced," +
                " usr.nameFull as full_name," +
                " prod.product_name as product_name," +
                " rep.count as count," +
                " rep.time_produced as time_produced" +
                " from records as rep, Products as prod, user as usr" +
                " where prod.id == rep.id_product and usr.id == rep.id_worker" +
                " order by rep.time_produced"
    )
    fun getDailyReports(): List<RecordShopReportOut>
}
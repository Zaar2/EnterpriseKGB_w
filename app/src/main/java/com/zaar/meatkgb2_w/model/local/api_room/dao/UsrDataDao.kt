package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_w.data.UserDescription
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb

@Dao
interface UsrDataDao: BaseDao<UserDb> {
    @Query("delete from user")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDb: UserDb): Long

    @Transaction
    suspend fun insertWithReplace(usrData: UserDb): Long {
        deleteAll()
        return insert(usrData)
    }

    @Query("select idWorkshop from user limit 1")
    suspend fun getIdWorkshop(): Long

    @Query("select id_one_more_workshop from user limit 1")
    suspend fun getIdMoreWorkshop(): Long

    @Query("select id_type_role from shop")
    suspend fun getRoleByShop(): Long

    @Query("select " +
            "shop.short_name as shopShortName, " +
            "user.appointment as appointment," +
            " user.nameShort as userShortName " +
            "from user, shop")
    suspend fun getUserDescription(): UserDescription
}
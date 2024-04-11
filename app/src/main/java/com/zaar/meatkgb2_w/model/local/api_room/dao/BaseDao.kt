package com.zaar.meatkgb2_w.model.local.api_room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: List<T>): Int

    @Delete
    fun delete(obj: T): Int

    @Delete
    fun delete(obj: List<T>): Int
}
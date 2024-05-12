package com.zaar2.meatKGB_w.model.local.api_room.entityDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ShopDb(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "name") var nameShop: String,
    @ColumnInfo(name = "nonManufacture_isChecked") val nonManufactureIsChecked: String,
    @ColumnInfo(name = "id_type_role") val idTypeRole: Int,
    @ColumnInfo(name = "id_status") val idStatus: Int,
    @ColumnInfo(name = "short_name") val shortName: String,
)

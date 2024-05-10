package com.zaar.meatKGB_w.data

import androidx.room.ColumnInfo

data class LogPass(
    @ColumnInfo(name = "enterpriseId") var enterpriseId: String? = null,
    @ColumnInfo(name = "usr_login") var usrLogin: String? = null,
    @ColumnInfo(name = "usr_pass") var usrPass: String? = null
) {
    fun isEmpty(): Boolean =
        (enterpriseId?.isEmpty() ?: true
                || usrPass?.isEmpty() ?: true
                || usrLogin?.isEmpty() ?: true)
}

package com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi

data class UserApi(
    var id: Long = -1,
    var full_name: String = "",
    var short_name: String = "",
    var appointment: String = "",
    var id_workshop: Long = -1,
    var id_one_more_workshop: Long = -1,
    var id_status: Long = -1,
    var usr_login: String,
)

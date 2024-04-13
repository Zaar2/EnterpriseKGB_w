package com.zaar.meatkgb2_w.model.mappers.apiToDb

import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.UserApi

class UserMapperApiToDb(
    private val userApi: UserApi,
) {
    fun execute(): UserDb =
        UserDb(
            id = userApi.id,
            nameFull = userApi.full_name,
            nameShort = userApi.short_name,
            appointment = userApi.appointment,
            idWorkshop = userApi.id_workshop,
            usrLogin = userApi.usr_login,
            idOneMoreWorkshop = userApi.id_one_more_workshop,
        )
}
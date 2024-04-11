package com.zaar.meatkgb2_w.model.mappers.apiToDb

import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.UserApi

class UserMapperApiToDb(
    private val userApi: UserApi,
) {
    fun execute(): UserDb =
        UserDb(
            id = userApi.id,
            nameFull = userApi.nameFull,
            nameShort = userApi.nameShort,
            appointment = userApi.appointment,
            idWorkshop = userApi.idWorkshop
        )
}
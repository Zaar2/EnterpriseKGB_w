package com.zaar2.meatKGB_w.model.mappers.remoteToDb

import com.zaar2.meatKGB_w.model.local.api_room.entityDb.UserDb
import com.zaar2.meatKGB_w.model.remote.entityRemote.UserRemote

class UserMapperRemoteToDb(
    private val userRemote: UserRemote,
) {
    fun execute(): UserDb =
        UserDb(
            id = userRemote.id,
            nameFull = userRemote.full_name,
            nameShort = userRemote.short_name,
            appointment = userRemote.appointment,
            idWorkshop = userRemote.id_workshop,
            usrLogin = userRemote.usr_login,
            idOneMoreWorkshop = userRemote.id_one_more_workshop,
        )
}
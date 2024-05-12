package com.zaar2.meatKGB_w.model.local

/**
 * so far, this class is not using in the app
 */
class EnabledAppUser private constructor() {
    var enterpriseName: String? = ""
    var appointment: String? = ""
    var usrFullName: String? = ""
    var usrLogin: String? = ""
    var sessionID = ""
    var isInit = false

    fun reset() {
        enterpriseName = null
        appointment = null
        usrFullName = null
        usrLogin = null
    }

    companion object {
        @get:Synchronized
        var instance: EnabledAppUser? = null
            get() {
                if (field == null) {
                    field = EnabledAppUser()
                    field!!.isInit = true
                }
                return field
            }
            private set
    }
}
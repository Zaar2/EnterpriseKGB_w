package com.zaar.meatkgb2_w.utilities.types

/**
 * Tags when exchanging data between fragments with use **Navigation**
 */
enum class tegsExchangingBetweenFragment(val value: String) {
    SOURCE("source"),
    ACTION("action"),
    VALUE("value"),
    LOGIN(TypeKeyForStore.KEY_USR_LOG.value),
    PASSWORD(TypeKeyForStore.KEY_USR_PASS.value),
    ENTERPRISE_ID(TypeKeyForStore.KEY_ENTERPRISE_ID.value)

}
package com.zaar.meatKGB_w.utilities.types

/**
 * Tags when exchanging data between fragments with use **Navigation**
 */
enum class TegExchangingBetweenFragment(val value: String) {
    SOURCE("source"),
    ACTION("action"),
    VALUE("value"),
    LOGIN(TypeKeyForStore.KEY_USR_LOG.value),
    PASSWORD(TypeKeyForStore.KEY_USR_PASS.value),
    ENTERPRISE_ID(TypeKeyForStore.KEY_ENTERPRISE_ID.value),
    SHOP_ID(TypeKeyForStore.KEY_SHOP_ID.value),
}
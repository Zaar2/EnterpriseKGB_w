package com.zaar.meatkgb2_w.model.repository

import android.content.Context
import com.zaar.meatkgb2_w.data.UserDescription
import com.zaar.meatkgb2_w.model.local.api_room.database.Database
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ShopDb
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.UserDb
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


class LocalDBRepositoryImpl(
    context: Context,
): LocalDBRepository {
    private val db: Database? = Database.getINSTANCE(context)

    override fun getCryptoIdEnterprise(): String? {
        var name: String? = null
        val es = Executors.newSingleThreadExecutor()

        val names: Future<List<String>> = es.submit(Callable {
            db?.enterpriseDao()?.getCryptoIdEnterprise()
        })

        try {
            name = names.get()[0]
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return name
    }

    override suspend fun getIdEnterprise(): String? =
        db?.enterpriseDao()?.getIdEnterprise()?.let {
            if (it.isEmpty()) ""
            else it[0]
        }

    override suspend fun userInsertWithReplace(userDb: UserDb): Long =
        db?.usrDataDao()?.insertWithReplace(userDb)?.let {
            if (it >= 0) it
            else -1L
        } ?: -1

    override suspend fun shopInsertWithReplace(shopDb: ShopDb): Long =
        db?.shopDao()?.insertWithReplace(shopDb)?.let {
            if (it >= 0) it
            else -1L
        } ?: -1

    override suspend fun productsInsertWithReplace(productsDb: List<ProductDb>): LongArray =
        db?.productDao()?.insertWithReplace(productsDb)?.let {
            if (it.isNotEmpty()) it
            else longArrayOf()
        } ?: longArrayOf()

    override suspend fun productsInsert(productsDb: List<ProductDb>): LongArray =
        db?.productDao()?.insert(productsDb)?.let {
            if (it.isNotEmpty()) it
            else longArrayOf()
        } ?: longArrayOf()

    override suspend fun getIdWorkshop(): Long =
        db?.usrDataDao()?.getIdWorkshop()?.let {
            if (it >= 0) it
            else -1L
        } ?: -1

    override suspend fun getIdMoreWorkshop(): Long =
        db?.usrDataDao()?.getIdMoreWorkshop()?.let {
            if (it >= 0) it
            else -1L
        } ?: -1

    override suspend fun getIdRoleByShop(): Long =
        db?.usrDataDao()?.getRoleByShop()?.let {
            if (it >= 0) it
            else -1L
        } ?: -1

    override suspend fun getUserDescription(): UserDescription? =
        db?.usrDataDao()?.getUserDescription()

    override suspend fun getProduct(): List<String>? =
        db?.productDao()?.getProduct()

    override suspend fun getMeByProduct(productName: String): String {
        return db?.productDao()?.getMeByProduct(productName) ?: ""
    }
}
package com.zaar.meatkgb2_w.model.useCase

import android.content.Context
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.ProductDb
import com.zaar.meatkgb2_w.model.mappers.apiToDb.ProductMapperApiToDb
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatkgb2_w.model.repository.RemoteRepositoryImpl

class UpdateProductUseCase(
    private val sessionId: String,
    private val enterpriseId: String,
    private val idWorkshop: Long = -1,
    private val myContext: Context
) {
    suspend fun execute(): Boolean {
        val productsApi = RemoteRepositoryImpl().getProducts(
            sessionId = sessionId,
            idWorkshop = idWorkshop,
            enterpriseID = enterpriseId
        )
        return if (!productsApi.isNullOrEmpty()) {
            val productsDb = productsApi.run {
                val productsOutput = mutableListOf<ProductDb>()
                forEach {
                    productsOutput.add(ProductMapperApiToDb(it).execute())
                }
                productsOutput
            }
            val idsProductInserted = myContext.let {
                LocalDBRepositoryImpl(it)
                    .productInsertWithReplace(productsDb)
            }
            idsProductInserted.size == productsDb.size
        } else false
    }
}
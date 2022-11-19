package com.aim2u.test_wings.data.repository

import androidx.annotation.WorkerThread
import com.aim2u.test_wings.data.datasource.local.ProductLocalDataSource
import com.aim2u.test_wings.data.datasource.local.database.dao.ProductDao
import com.aim2u.test_wings.data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(val productDao: ProductDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allProduct: Flow<List<Product>> = productDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(productList: List<Product>) {
        productDao.insertAll(*productList.toTypedArray())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        productDao.deleteAll()
    }

}
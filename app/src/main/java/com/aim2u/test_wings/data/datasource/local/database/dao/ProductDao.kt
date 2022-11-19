package com.aim2u.test_wings.data.datasource.local.database.dao

import androidx.room.*
import com.aim2u.test_wings.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)

    @Delete
    fun delete(user: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM product_table")
    fun getAll(): Flow<List<Product>>

}
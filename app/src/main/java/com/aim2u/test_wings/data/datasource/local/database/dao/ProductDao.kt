package com.aim2u.test_wings.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aim2u.test_wings.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(user: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM product_table")
    fun getAll(): Flow<List<Product>>

}
package com.aim2u.test_wings.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aim2u.test_wings.data.model.Product

@Dao
interface TransactionDetailDao {
    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(user: Product)

    @Query("SELECT * FROM product_table")
    fun getAll(): List<Product>

}
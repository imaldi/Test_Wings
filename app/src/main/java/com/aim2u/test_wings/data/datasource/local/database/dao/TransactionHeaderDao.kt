package com.aim2u.test_wings.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aim2u.test_wings.data.model.TransactionHeader

@Dao
interface TransactionHeaderDao {
    @Insert
    fun insertAll(vararg product: TransactionHeader)

    @Delete
    fun delete(user: TransactionHeader)

    @Query("SELECT * FROM transaction_header")
    fun getAll(): List<TransactionHeader>

}
package com.aim2u.test_wings.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aim2u.test_wings.data.model.Login
import com.aim2u.test_wings.data.model.Product

@Dao
interface LoginDao {
    @Query("SELECT * FROM login_table")
    fun getAll(): List<Login>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(login: Login)

}
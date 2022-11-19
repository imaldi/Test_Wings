package com.aim2u.test_wings.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aim2u.test_wings.data.model.Login

@Dao
interface LoginDao {
    @Query("SELECT * FROM login_table")
    fun getAll(): List<Login>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(login: Login)

    @Query("SELECT * FROM login_table WHERE user = :userName AND password = :password ")
    fun login(userName: String, password: String): Login?

}
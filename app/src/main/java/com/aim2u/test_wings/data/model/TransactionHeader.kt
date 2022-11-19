package com.aim2u.test_wings.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.TypeConverter

@Entity(
    tableName = "transaction_header",
    primaryKeys = ["document_code","document_number"],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Login::class,
            parentColumns = arrayOf("user",),
            childColumns = arrayOf("user_name")
        )
    )
    )
data class TransactionHeader (
    @ColumnInfo(name = "document_code")
    val documentCode: String,
    @ColumnInfo(name = "document_number")
    val documentNumber: String,
    @ColumnInfo(name = "user_name")
    val user: String,
    @ColumnInfo(name = "total")
    val total: Int,
    @ColumnInfo(name = "date")
    val date: String,
    )
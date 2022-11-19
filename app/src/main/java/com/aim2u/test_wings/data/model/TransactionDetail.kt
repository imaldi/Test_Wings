package com.aim2u.test_wings.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction_detail",
//    primaryKeys = ["document_code","document_number"],
//    ignoredColumns = [
//        "product_name",
//        "discount",
//        "dimension",
//        "user_name",
//        "total",
//        "date"
//    ]
)
data class TransactionDetail(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "document_code")
    val documentCode: String,
    @ColumnInfo(name = "document_number")
    val documentNumber: String,
    @ColumnInfo(name = "quantity")
    val quantity: String,
    @ColumnInfo(name = "subtotal")
    val subTotal: String,
//    @Embedded
//    val product: Product,
    @ColumnInfo(name = "product_code")
    val productCode: String, // foreign key
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "unit")
    val unit: String,
    @ColumnInfo(name = "currency")
    val currency: String,


    )
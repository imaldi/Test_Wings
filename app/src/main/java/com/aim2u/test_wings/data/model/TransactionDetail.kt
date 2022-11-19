package com.aim2u.test_wings.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    tableName = "transaction_detail",
    ignoredColumns = [
        "product_name",
        "discount",
        "dimension",
        "user_name",
        "total",
        "date"
    ]
)
data class TransactionDetail(
    @Embedded
    val transactionHeader: TransactionHeader,
    @ColumnInfo(name = "quantity")
    val quantity: String,
    @ColumnInfo(name = "subtotal")
    val subTotal: String,
    @Embedded
    val product: Product,
//    @ColumnInfo(name = "product_code_trans")
//    val productCode: String, // foreign key
//    @ColumnInfo(name = "price_trans")
//    val price: Int,
//    @ColumnInfo(name = "unit_trans")
//    val unit: String,
//    @ColumnInfo(name = "currency_trans")
//    val currency: String,


    )
package com.aim2u.test_wings.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "product_code")
    val productCode: String,
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "discount")
    val discount: Int,
    @ColumnInfo(name = "dimension")
    val dimension: String,
    @ColumnInfo(name = "unit")
    val unit: String,

){
    @Ignore
    var isSelected: Boolean = false
}

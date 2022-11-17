package com.aim2u.test_wings.data.model

data class Product(
    val productCode: String,
    val productName: String,
    val price: Int,
    val currency: String,
    val discount: Int,
    val dimension: String,
    val unit: String,
)

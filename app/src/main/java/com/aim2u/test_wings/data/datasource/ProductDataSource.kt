package com.aim2u.test_wings.data.datasource

import com.aim2u.test_wings.data.model.Product

class ProductDataSource {
    fun loadProducts(): List<Product>{
        return listOf(
            Product(
                productCode = "ASBJDBCBA",
                productName = "So Klin Pewangi",
                price = 15000,
                discount = 10,
                currency = "Rp",
                dimension = "-",
                unit = "PCS",
            ),
            Product(
                productCode = "ASFDAFAS",
                productName = "Giv Biru",
                price = 11000,
                discount = 5,
                currency = "Rp",
                dimension = "-",
                unit = "PCS",
            ),
            Product(
                productCode = "ASDFABCBA",
                productName = "So Klin Liquid",
                price = 18000,
                discount = 0,
                currency = "Rp",
                dimension = "-",
                unit = "PCS",
            ),
            Product(
                productCode = "FSDFSFA",
                productName = "Giv Kuning",
                price = 10000,
                discount = 15,
                currency = "Rp",
                dimension = "-",
                unit = "PCS",
            ),
            Product(
                productCode = "DFSFSFDSF",
                productName = "Liveboy Merah",
                price = 14000,
                discount = 12,
                currency = "Rp",
                dimension = "-",
                unit = "PCS",
            ),
        )
    }
}
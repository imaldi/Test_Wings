package com.aim2u.test_wings.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aim2u.test_wings.R
import com.aim2u.test_wings.adapter.ItemAdapter
import com.aim2u.test_wings.data.datasource.ProductDataSource
import com.aim2u.test_wings.databinding.ActivityLoginBinding
import com.aim2u.test_wings.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myDataSet = ProductDataSource().loadProducts()

        val recyclerView = binding.productListId
        val adapter = ItemAdapter(myDataSet)
        recyclerView.adapter = adapter
        // size of this RecyclerView is fixed
        recyclerView.setHasFixedSize(true)
//        adapter.notifyDataStateChanged

    }
}
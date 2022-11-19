package com.aim2u.test_wings.ui.shared_view_model

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.aim2u.test_wings.data.datasource.ProductDataSource
import com.aim2u.test_wings.data.datasource.local.ProductLocalDataSource
import com.aim2u.test_wings.data.datasource.local.TransactionDetailLocalDataSource
import com.aim2u.test_wings.data.datasource.local.TransactionHeaderLocalDataSource
import com.aim2u.test_wings.data.datasource.local.database.dao.ProductDao
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.data.model.TransactionDetail
import com.aim2u.test_wings.data.model.TransactionHeader
import com.aim2u.test_wings.data.repository.ProductRepository
import com.aim2u.test_wings.data.repository.TransactionDetailRepository
import com.aim2u.test_wings.data.repository.TransactionHeaderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedViewModel(
    private val _productRepository: ProductRepository,
    private val _transactionHeaderRepository: TransactionHeaderRepository,
    private val _transactionDetailRepository: TransactionDetailRepository,
    private val scope: CoroutineScope
) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

//    private val _allProduct = MutableLiveData<List<Product>>()
    val allProduct: LiveData<List<Product>> = _productRepository.allProduct.asLiveData()

    private val _selectedProduct = MutableLiveData<List<Product>>(listOf())
    val selectedProduct: LiveData<List<Product>> = _selectedProduct

    private val _transactionHeader = MutableLiveData<TransactionHeader?>()
    val transactionHeader: LiveData<TransactionHeader?> = _transactionHeader

    private val _transactionDetail = MutableLiveData<List<TransactionDetail>>(listOf())
    val transactionDetail: LiveData<List<TransactionDetail>> = _transactionDetail

    fun setAllProduct() {
        scope.launch {
            _productRepository.deleteAll()
            _productRepository.insertAll(ProductDataSource().loadProducts())
            val products = _productRepository.allProduct
            Log.d("Products",products.toString())
        }
    }

    // Define ViewModel factory in a companion object
//    companion object {
//
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//
//        }
//    }

}

class SharedViewModelFactory(
    private val _productRepository: ProductRepository,
    private val _transactionHeaderRepository: TransactionHeaderRepository,
    private val _transactionDetailRepository: TransactionDetailRepository,
    private val scope: CoroutineScope
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(
                _productRepository,
                _transactionHeaderRepository,
                _transactionDetailRepository,
                scope
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
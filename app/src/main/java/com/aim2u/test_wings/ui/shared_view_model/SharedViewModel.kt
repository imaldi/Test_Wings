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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SharedViewModel(
    private val _productRepository: ProductRepository,
    private val _transactionHeaderRepository: TransactionHeaderRepository,
    private val _transactionDetailRepository: TransactionDetailRepository,
    private val scope: CoroutineScope
) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _allProduct = MutableLiveData<List<Product>>(
//    _productRepository.allProduct.asLiveData().value
    listOf()
    )
    val allProduct: LiveData<List<Product>> = _allProduct

    private val _selectedProduct = MutableLiveData<List<Product>>(listOf())
    val selectedProduct: LiveData<List<Product>> = _selectedProduct

    private val _transactionHeader = MutableLiveData<TransactionHeader?>()
    val transactionHeader: LiveData<TransactionHeader?> = _transactionHeader

    private val _transactionDetail = MutableLiveData<List<TransactionDetail>>(listOf())
    val transactionDetail: LiveData<List<TransactionDetail>> = _transactionDetail

    private val _simpleNumber = MutableLiveData<Int>(0)
    val simpleNumber: LiveData<Int> = _simpleNumber

    fun setAllProduct() {
//        var products: List<Product> = listOf()
//        scope.launch {
////            _productRepository.deleteAll()
//            val rowIds = _productRepository.insertAll(ProductDataSource().loadProducts())
//            products = _productRepository.allProduct.asLiveData().value ?: listOf()
//
//            Log.d("Products rowIds",rowIds.toString())
//        }
//            _allProduct.value = ProductDataSource().loadProducts()
////            _allProduct.value = products
    }

    fun incrementSimpleNumber(){
//        _simpleNumber.value?.plus(1)
        _simpleNumber.value = _simpleNumber.value?.plus(1)
        Log.d("Simple Number",_simpleNumber.value.toString())
        Log.d("All Product",_allProduct.value.toString())
//        viewModelScope.launch {
//            _productRepository.insert(ProductDataSource().loadProducts()[_simpleNumber.value?:0])
//        }
        _allProduct.value = ProductDataSource().loadProducts()
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
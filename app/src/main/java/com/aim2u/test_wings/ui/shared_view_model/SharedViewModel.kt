package com.aim2u.test_wings.ui.shared_view_model

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.aim2u.test_wings.data.model.Login
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.data.model.TransactionDetail
import com.aim2u.test_wings.data.model.TransactionHeader
import com.aim2u.test_wings.data.repository.ProductRepository
import com.aim2u.test_wings.data.repository.TransactionDetailRepository
import com.aim2u.test_wings.data.repository.TransactionHeaderRepository

class SharedViewModel(
    private val _productRepository: ProductRepository,
    private val _transactionHeaderRepository: TransactionHeaderRepository,
    private val _transactionDetailRepository: TransactionDetailRepository,
) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean?>(null)
    val isLoggedIn: LiveData<Boolean?> = _isLoggedIn

    private var _loggedInUser = MutableLiveData<Login?>(null)
    val loggedInUser: LiveData<Login?> = _loggedInUser

    val allProduct: LiveData<List<Product>> = _productRepository.allProduct.asLiveData()

    private val _selectedProduct = MutableLiveData<List<Product>>(listOf())
    val selectedProduct: LiveData<List<Product>> = _selectedProduct

    private val _transactionHeader = MutableLiveData<TransactionHeader?>()
    val transactionHeader: LiveData<TransactionHeader?> = _transactionHeader

    private val _transactionDetail = MutableLiveData<List<TransactionDetail>>(listOf())
    val transactionDetail: LiveData<List<TransactionDetail>> = _transactionDetail

    private val _simpleNumber = MutableLiveData<Int>(0)
    val simpleNumber: LiveData<Int> = _simpleNumber

    fun incrementSimpleNumber() {
        // failed gini
        // _simpleNumber.value?.plus(1)
        _simpleNumber.value = _simpleNumber.value?.plus(1)
        Log.d("Simple Number", _simpleNumber.value.toString())
        Log.d("All Product", allProduct.value.toString())
    }

    fun updateLoginUser(loggedInUser: Login){
        _loggedInUser.value = loggedInUser
    }

    fun updateLoginStatus(isLogin: Boolean){
        _isLoggedIn.value = isLogin
    }
}

class SharedViewModelFactory(
    private val _productRepository: ProductRepository,
    private val _transactionHeaderRepository: TransactionHeaderRepository,
    private val _transactionDetailRepository: TransactionDetailRepository,
//    private val scope: CoroutineScope
) : ViewModelProvider.Factory {
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
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
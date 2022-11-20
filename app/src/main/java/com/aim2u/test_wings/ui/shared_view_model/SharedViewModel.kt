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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
//    val listProduct = allProduct.value

    private val _selectedProduct: MutableLiveData<MutableList<Product>> = MutableLiveData(
        mutableListOf<Product>()
    )

    // fixme ganti MutableList ke List nanti2
    val selectedProduct: LiveData<MutableList<Product>> = _selectedProduct

    private val _detailedProduct = MutableLiveData<Product>()
    val detailedProduct: LiveData<Product> = _detailedProduct

    private val _transactionHeader = MutableLiveData<TransactionHeader?>()
    val transactionHeader: LiveData<TransactionHeader?> = _transactionHeader

    private val _transactionDetail =
        MutableLiveData<MutableList<TransactionDetail>>(mutableListOf())
    val transactionDetail: LiveData<MutableList<TransactionDetail>> = _transactionDetail

    private val _quantityList = MutableLiveData<MutableList<Int>>()
    val quantityList = _quantityList

    private val _simpleNumber = MutableLiveData<Int>(0)
    val simpleNumber: LiveData<Int> = _simpleNumber

    fun incrementSimpleNumber() {
        // failed gini
        // _simpleNumber.value?.plus(1)
        _simpleNumber.value = _simpleNumber.value?.plus(1)
        Log.d("Simple Number", _simpleNumber.value.toString())
        Log.d("All Product", allProduct.value.toString())
    }

    fun setDetailedProduct(productCode: String) {
        _detailedProduct.value =
            _selectedProduct.value?.filter { it.productCode == productCode }?.toList()?.first()
    }

    fun updateLoginUser(loggedInUser: Login) {
        _loggedInUser.value = loggedInUser
    }

    fun updateLoginStatus(isLogin: Boolean) {
        _isLoggedIn.value = isLogin
//        selectedProduct.value?.set()
    }

//    fun updateSelectedTransDetail(index: Int,newValue:String){
//        if(_transactionDetail.value != null && (transactionDetail.value?.isNotEmpty() ?: false)){
//            _transactionDetail.value?.set(index,transactionDetail.value?.get(index)?.copy(quantity = newValue)!!)
//        }
//    }

    fun updateQuantityList(index: Int,newValue:Int){
        if(_quantityList.value != null && (quantityList.value?.isNotEmpty() ?: false)){
            _quantityList.value?.set(index,newValue)
        }
    }

    fun setSelectedProductList(
        resetSelected: Boolean = false
//        index: Int, selected: Boolean, product: Product
    ) {
        if (resetSelected)
            _selectedProduct.value =
                allProduct.value?.map { it.isSelected = false; it }?.toMutableList()
        else
            _selectedProduct.value = allProduct.value?.toMutableList()

//        selectedProduct.value?.set(index, Pair(product,selected))
    }

    fun setSelectedProduct(index: Int, selected: Boolean) {
        _selectedProduct.value?.get(index)?.isSelected =
            !(_selectedProduct.value?.get(index)?.isSelected ?: true)
//        _selectedProduct.value.set(index,_selectedProduct.value.get(index).copy(isS))
    }

    fun clearTransactionHeaderAndSelectedProduct() {
        _transactionHeader.value = null
        _transactionDetail.value = mutableListOf()
//        _selectedProduct.value?.clear()
        setSelectedProductList(resetSelected = true)
    }

    fun initTransactionHeader() {
        val allowedCharsDocumentCode = ('A'..'Z')
        var documentCode = (1..3)
            .map { allowedCharsDocumentCode.random() }
            .joinToString("")
        val allowedCharsDocumentNumber = ('0'..'9')
        var documentNumber = (1..3)
            .map { allowedCharsDocumentCode.random() }
            .joinToString("")
        var isUnique: Boolean
        viewModelScope.launch(Dispatchers.IO) {
            isUnique = _transactionHeaderRepository.checkDocumentCodeAndNumber(
                documentCode,
                documentNumber
            )
            withContext(Dispatchers.Main) {
                if (isUnique) {
                    _transactionHeader.value =
                        TransactionHeader(documentCode, documentNumber, "", 0, "")

                    var transactionDetailNewItems =
                        _selectedProduct.value?.filter { it.isSelected }?.map {
                            TransactionDetail(
                                documentCode,
                                documentNumber,
                                quantity = "1",
                                productCode = it.productCode,
                                subTotal = "${it.price}",
                                price = it.price,
                                unit = it.unit,
                                currency = it.currency
                            )
                        }?.toList() ?: listOf()
                    _transactionDetail.value?.clear()
                    _transactionDetail.value?.addAll(transactionDetailNewItems)
                    _quantityList.value?.addAll(transactionDetailNewItems.map { it.quantity.toInt() }.toMutableList())
                }
                // nanti cari tau copy nya ni copywith ga
//                var a = _transactionHeader.value?.copy(user = "Aldi")
            }
        }

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
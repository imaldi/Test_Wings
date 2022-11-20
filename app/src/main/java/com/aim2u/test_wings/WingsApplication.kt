package com.aim2u.test_wings

import android.app.Application
import com.aim2u.test_wings.data.datasource.local.database.local_database.TransactionDatabase
import com.aim2u.test_wings.data.repository.ProductRepository
import com.aim2u.test_wings.data.repository.TransactionDetailRepository
import com.aim2u.test_wings.data.repository.TransactionHeaderRepository
import com.aim2u.test_wings.ui.login_fragment.data.LoginDataSource
import com.aim2u.test_wings.ui.login_fragment.data.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WingsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { TransactionDatabase.getDatabase(this, applicationScope) }

    // FIXME fix login repository
    val loginRepository by lazy { LoginRepository(LoginDataSource(database.loginDao())) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    val transactionHeaderRepository by lazy { TransactionHeaderRepository(database.transactionHeaderDao()) }
    val transactionDetailRepository by lazy { TransactionDetailRepository(database.transactionDetailDao()) }
}
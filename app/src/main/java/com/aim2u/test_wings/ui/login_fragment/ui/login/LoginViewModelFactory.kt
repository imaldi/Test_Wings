package com.aim2u.test_wings.ui.login_fragment.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aim2u.test_wings.data.datasource.local.database.dao.LoginDao
import com.aim2u.test_wings.ui.login_fragment.data.LoginDataSource
import com.aim2u.test_wings.ui.login_fragment.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(val loginRepository: LoginRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
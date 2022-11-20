package com.aim2u.test_wings.data.repository

import androidx.annotation.WorkerThread
import com.aim2u.test_wings.data.datasource.local.TransactionHeaderLocalDataSource
import com.aim2u.test_wings.data.datasource.local.database.dao.TransactionHeaderDao
import com.aim2u.test_wings.data.model.Login
import com.aim2u.test_wings.ui.login_fragment.data.Result

class TransactionHeaderRepository(val _transactionHeaderDao: TransactionHeaderDao) {
    @WorkerThread
    suspend fun checkDocumentCodeAndNumber(documentCode: String, documentNumber: String): Boolean {
        // handle login
        val result = _transactionHeaderDao.checkDocumentCodeAndNumber(documentCode, documentNumber)

        if (result.isEmpty()) {
            return true
        }

        return false
    }
}
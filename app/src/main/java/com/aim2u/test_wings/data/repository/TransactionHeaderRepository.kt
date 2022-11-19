package com.aim2u.test_wings.data.repository

import com.aim2u.test_wings.data.datasource.local.TransactionHeaderLocalDataSource
import com.aim2u.test_wings.data.datasource.local.database.dao.TransactionHeaderDao

class TransactionHeaderRepository(val transactionHeaderDao: TransactionHeaderDao) {
}
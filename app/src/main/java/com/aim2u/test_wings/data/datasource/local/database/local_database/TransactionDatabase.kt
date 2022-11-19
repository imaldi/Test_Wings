package com.aim2u.test_wings.data.datasource.local.database.local_database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aim2u.test_wings.data.datasource.ProductDataSource
import com.aim2u.test_wings.data.datasource.local.database.dao.LoginDao
import com.aim2u.test_wings.data.datasource.local.database.dao.ProductDao
import com.aim2u.test_wings.data.datasource.local.database.dao.TransactionDetailDao
import com.aim2u.test_wings.data.datasource.local.database.dao.TransactionHeaderDao
import com.aim2u.test_wings.data.model.Login
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.data.model.TransactionDetail
import com.aim2u.test_wings.data.model.TransactionHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//class TransactionDatabase {
//}

@Database(
    entities = [Login::class, Product::class, TransactionHeader::class, TransactionDetail::class],
    version = 1,
    exportSchema = false
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun productDao(): ProductDao
    abstract fun transactionHeaderDao(): TransactionHeaderDao
    abstract fun transactionDetailDao(): TransactionDetailDao

    private class PenjualanDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("Callback onCreate",ProductDataSource().loadProducts().toString())

            INSTANCE?.let { database ->
                scope.launch {
                    var productDao = database.productDao()

                    // Delete all content here.
                    productDao.deleteAll()

                    productDao.insertAll(*ProductDataSource().loadProducts().toTypedArray())
                    Log.d("Callback Database",productDao.getAll().toString())
                }
            }
        }
    }


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TransactionDatabase? = null


        fun getDatabase(context: Context,scope: CoroutineScope
        ): TransactionDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
//            scope.launch {
//
//            Log.d("Get Database", INSTANCE.productDao().getAll())
//            }

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "penjualan"
                )
//                    .addCallback(PenjualanDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

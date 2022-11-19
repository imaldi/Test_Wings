package com.aim2u.test_wings.data.datasource.local.local_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aim2u.test_wings.data.datasource.local.dao.LoginDao
import com.aim2u.test_wings.data.datasource.local.dao.ProductDao
import com.aim2u.test_wings.data.datasource.local.dao.TransactionDetailDao
import com.aim2u.test_wings.data.datasource.local.dao.TransactionHeaderDao
import com.aim2u.test_wings.data.model.Login
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.data.model.TransactionDetail
import com.aim2u.test_wings.data.model.TransactionHeader

//class TransactionDatabase {
//}

@Database(
    entities = [Login::class, Product::class, TransactionHeader::class, TransactionDetail::class],
    version = 1,
    exportSchema = false
)
public abstract class TransactionDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun productDao(): ProductDao
    abstract fun transactionHeaderDao(): TransactionHeaderDao
    abstract fun transactionDetailDao(): TransactionDetailDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

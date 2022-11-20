package com.aim2u.test_wings.ui.login_fragment.data

import com.aim2u.test_wings.data.datasource.local.database.dao.LoginDao
import com.aim2u.test_wings.data.model.Login
import com.aim2u.test_wings.ui.login_fragment.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(val loginDao: LoginDao) {

    fun login(username: String, password: String): Result<Login> {
        try {
            // TODO: handle loggedInUser authentication
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            var loginUser = loginDao.login(username, password)
            return if (loginUser != null) Result.Success(loginUser) else return Result.Error(
                IOException("User Not Found", Throwable())
            )
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
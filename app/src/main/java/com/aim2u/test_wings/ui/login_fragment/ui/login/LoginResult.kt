package com.aim2u.test_wings.ui.login_fragment.ui.login

import com.aim2u.test_wings.data.model.Login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Login? = null,
    val error: Int? = null
)
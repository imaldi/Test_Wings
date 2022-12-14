package com.aim2u.test_wings.ui.login_fragment.ui.login

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aim2u.test_wings.R
import com.aim2u.test_wings.WingsApplication
import com.aim2u.test_wings.databinding.FragmentLoginBinding
import com.aim2u.test_wings.ui.shared_view_model.SharedViewModel
import com.aim2u.test_wings.ui.shared_view_model.SharedViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels {
        // FIXME ini mungkin error karena null
        var application = (activity?.application as WingsApplication)
        Log.d("activityViewModels", "Hello")

        SharedViewModelFactory(
            application.productRepository,
            application.transactionHeaderRepository,
            application.transactionDetailRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        activity?.actionBar?.title = "Login";
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(
            (activity?.application as WingsApplication).loginRepository
        ))
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading




        sharedViewModel.isLoggedIn.observe(viewLifecycleOwner){
            if((it ?: false)){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProductListFragment())
            }
        }

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val isLogin = sharedPref?.getBoolean(getString(R.string.login_preference_file_key), false)
        if(isLogin!!) {
            sharedViewModel.updateLoginStatus(isLogin)
        }

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
//                    updateUiWithUser(it)
                    sharedViewModel.updateLoginUser(it)
                    sharedViewModel.updateLoginStatus(true)
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@let
                    with (sharedPref.edit()) {
                        putBoolean(getString(R.string.login_preference_file_key), true)
                        apply()
                    }
                }
//                findNavController()
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
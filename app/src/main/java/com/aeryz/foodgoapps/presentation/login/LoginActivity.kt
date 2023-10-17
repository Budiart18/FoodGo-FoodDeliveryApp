package com.aeryz.foodgoapps.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.aeryz.foodgoapps.R
import com.aeryz.foodgoapps.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.aeryz.foodgoapps.data.repository.UserRepositoryImpl
import com.aeryz.foodgoapps.databinding.ActivityLoginBinding
import com.aeryz.foodgoapps.presentation.main.MainActivity
import com.aeryz.foodgoapps.presentation.register.RegisterActivity
import com.aeryz.foodgoapps.utils.GenericViewModelFactory
import com.aeryz.foodgoapps.utils.highLightWord
import com.aeryz.foodgoapps.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private fun createViewModel(): LoginViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repository = UserRepositoryImpl(dataSource)
        return LoginViewModel(repository)
    }
    private val viewModel: LoginViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListener()
        observeResult()
    }

    private fun observeResult() {
        viewModel.loginResult.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    Toast.makeText(this, "Login Failed : ${it.exception?.message.orEmpty()}", Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                }
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setClickListener() {
        binding.btnLogin.setOnClickListener{
            doLogin()
        }
        binding.tvNavToRegister.highLightWord(getString(R.string.text_highlight_register_here)){
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun isFormValid(): Boolean {
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        return checkEmailValidation(email) && checkPasswordValidation(password, binding.layoutForm.tilPassword)
    }

    private fun checkPasswordValidation(password: String, tilPassword: TextInputLayout): Boolean {
        return if (password.isEmpty()){
            tilPassword.isErrorEnabled = true
            tilPassword.error = getString(R.string.text_error_password_empty)
            false
        } else if (password.length < 8){
            tilPassword.isErrorEnabled = true
            tilPassword.error = getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            tilPassword.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()){
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.etEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.etEmail.error = getString(R.string.text_error_email_not_valid)
            false
        } else {
            binding.layoutForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            viewModel.doLogin(email, password)
        }
    }

    private fun setupForm() {
        with(binding.layoutForm) {
            tilEmail.isVisible = true
            tilPassword.isVisible = true
        }
    }
}
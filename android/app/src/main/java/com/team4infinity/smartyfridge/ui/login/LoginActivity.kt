package com.team4infinity.smartyfridge.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.team4infinity.smartyfridge.R
import com.team4infinity.smartyfridge.ui.MainActivity
import com.team4infinity.smartyfridge.utils.TOAST_LOGIN_FAILED
import com.team4infinity.smartyfridge.utils.TOAST_LOGIN_SUCCESS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btn: Button
    private val viewModel: LoginActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        btn.setOnClickListener { v ->
            if (email.text.isEmpty() or password.text.isEmpty()){
                Toast.makeText(this,"Please enter Email and Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email.text.toString(),password.text.toString())
            viewModel.loggedIn.observe(this, { value ->
                if (value) {
                    Toast.makeText(this, TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show()
                    changeActivity()
                } else {
                    Toast.makeText(this, TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getCurrentUser() != null){
            println("Postoji user: ${viewModel.getCurrentUser()?.email}")
            changeActivity()
        }
    }

    private fun init(){
        email = findViewById(R.id.emailLogin)
        password = findViewById(R.id.passwordLogin)
        btn = findViewById(R.id.loginBtn)
    }
    private fun changeActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
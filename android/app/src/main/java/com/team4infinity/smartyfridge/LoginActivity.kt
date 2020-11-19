package com.team4infinity.smartyfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var btn:Button
    private lateinit var createAccount:TextView
    private val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        createAccount.setOnClickListener{txt->
            val intent=Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener { v ->
            auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnSuccessListener {
                authResult ->
                run {
                    Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener { exception -> Toast.makeText(this,"Email or password is wrong",Toast.LENGTH_SHORT).show() }
        }
    }
    private fun init(){
        auth = Firebase.auth
        email = findViewById(R.id.emailLogin)
        password = findViewById(R.id.passwordLogin)
        btn = findViewById(R.id.loginBtn)
        createAccount=findViewById(R.id.textCreateAccount)
    }
}
package com.team4infinity.smartyfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var auth :FirebaseAuth
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var btn:Button
    private val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        btn.setOnClickListener { v ->
            if (email.text.isEmpty() or password.text.isEmpty()){
                Toast.makeText(this,"Please enter Email and Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
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
        email = findViewById(R.id.emailLogin)
        password = findViewById(R.id.passwordLogin)
        btn = findViewById(R.id.loginBtn)
        //auth = FirebaseAuth.getInstance()
    }
}
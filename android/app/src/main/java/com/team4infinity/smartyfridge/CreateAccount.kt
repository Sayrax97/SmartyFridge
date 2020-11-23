package com.team4infinity.smartyfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.team4infinity.smartyfridge.models.User

class CreateAccount : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var db:DatabaseReference
    private lateinit var name:EditText
    private lateinit var surname:EditText
    private lateinit var email: EditText
    private lateinit var password:EditText
    private lateinit var confirmPassword:EditText
    private lateinit var createAccountBtn:Button
    private val TAG = "CreateAccount"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        init()
        createAccountBtn.setOnClickListener{btn->
            if(name.text.isEmpty())
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            else if(surname.text.isEmpty())
                Toast.makeText(this, "Please enter your surname", Toast.LENGTH_SHORT).show()
            else if(email.text.isEmpty())
                Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            else if(password.text.isEmpty())
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            else if(confirmPassword.text.isEmpty())
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show()
            else if(password.text.toString()!=confirmPassword.text.toString())
                Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show()
            else{
                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnSuccessListener{authResult->
                    var uid = authResult.user?.uid
                    if (uid != null){
                        val user = User(name.text.toString(), surname.text.toString(), email.text.toString(), password.text.toString())
                        db.child("users").child(uid).setValue(user)
                    }
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener{authResult->Toast.makeText(this, "Authentication error", Toast.LENGTH_SHORT).show()}
            }
        }
    }

    private fun init(){
        auth= Firebase.auth
        db = FirebaseDatabase.getInstance().reference
        name=findViewById(R.id.editName);
        surname=findViewById(R.id.editSurname);
        email=findViewById(R.id.editEmail);
        password=findViewById(R.id.editPassword);
        confirmPassword=findViewById(R.id.editConfirmPassword);
        createAccountBtn=findViewById(R.id.btnCreateAccount);

    }
}
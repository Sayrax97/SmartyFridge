package com.team4infinity.smartyfridge

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

import com.team4infinity.smartyfridge.models.User
import java.io.ByteArrayOutputStream


class CreateAccount : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    val SELECT_PICTURE = 1
    private lateinit var storage: StorageReference
    private lateinit var db:DatabaseReference
    private lateinit var name:EditText
    private lateinit var surname:EditText
    private lateinit var email: EditText
    private lateinit var password:EditText
    private lateinit var confirmPassword:EditText
    private lateinit var createAccountBtn:Button
    private lateinit var profilePictureImageVIew: ImageView
    private lateinit var imageUri: Uri
    private val TAG = "CreateAccount"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        init()
        createAccountBtn.setOnClickListener{ btn->
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
                createAuthUser()
            }
        }
    }

    private fun init(){
        auth= Firebase.auth
        storage = FirebaseStorage.getInstance().reference
        db = FirebaseDatabase.getInstance().reference
        name=findViewById(R.id.editName);
        surname=findViewById(R.id.editSurname);
        email=findViewById(R.id.editEmail);
        password=findViewById(R.id.editPassword);
        confirmPassword=findViewById(R.id.editConfirmPassword);
        createAccountBtn=findViewById(R.id.btnCreateAccount);
        profilePictureImageVIew = findViewById(R.id.editProfilePicture)
        profilePictureImageVIew.setOnClickListener {
            pickImage()
        }
    }
    private fun pickImage() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select profile picture"), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Picasso.get().load(data?.data).resize(500, 500).centerInside().onlyScaleDown().into(
                    profilePictureImageVIew
                )
                imageUri = data?.data!!
            }
        }
    }
    private fun uploadImage() {
        val currentUser = auth.currentUser
        val bitmap = (profilePictureImageVIew.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()
        storage.child("user").child(currentUser!!.uid).child("profile").putBytes(data)
//        storage.child(USER_CHILD).child(currentUser.getUid()).child("profile").putFile(imageUri);
    }
    private fun storeUser(uid: String){
        val user = User(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
        db.child("users").child(uid).setValue(user)
    }
    private fun createAuthUser(){
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnSuccessListener{ authResult->
            var uid = authResult.user?.uid
            if (uid != null){
                storeUser(uid)
            }
            else return@addOnSuccessListener
            uploadImage()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener{ authResult -> Toast.makeText(
                this,
                "Authentication error",
                Toast.LENGTH_SHORT
            ).show()}
    }
}
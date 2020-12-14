package com.team4infinity.smartyfridge

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.team4infinity.smartyfridge.models.Group
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

import com.team4infinity.smartyfridge.models.User
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random
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
    private lateinit var cbGroup:CheckBox
    private lateinit var groupID:EditText
    private var groupChecked:Boolean = false
    private lateinit var userGroupID:String
    private val FIREBASE_GROUP:String="Group"
    private val FIREBASE_GROUP_IDs:String="GroupIDs"
    private val TAG = "CreateAccount"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        init()

        cbGroup.setOnCheckedChangeListener{ cb, isChecked->
            if(isChecked){
                groupID.setVisibility(View.VISIBLE)
                groupChecked = true;
            }
            else{
                groupID.setVisibility(View.INVISIBLE)
                groupID.setText("")
                groupChecked = false;
            }
        }

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
//                    createGroup()
                    createGroupOptional()
                }
            }
        }

    private fun createGroup(){
        if(groupChecked){
            if(groupID.text.isEmpty()) {
                Toast.makeText(this, "Please enter group ID", Toast.LENGTH_SHORT).show()
            }
            else{
                db.child(FIREBASE_GROUP).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val groups = HashMap<String,Group?>()
                        for (groupSnapshot in snapshot.getChildren()) {
                            groups.put(groupSnapshot.key.toString(), groupSnapshot.getValue<Group>())
                            val x=5;
                        }
                        var pom: Int = 0;
                        groups.forEach{(key,value)->
                            if(value != null)
                                if(value.id == groupID.text.toString())
                                    userGroupID=key;
                        }
                        //TODO
                        //createAuthUser()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
        else {
            val key: String = db.child(FIREBASE_GROUP).push().key.toString()
            var exists: Boolean = true
            var idGroup: String = ""
            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    while (exists) {
                        var random: Int = Random.nextInt(0, key.length - 6)
                        idGroup = key.substring(random, random + 6)
                        if (snapshot.hasChild(FIREBASE_GROUP)) {
                            val groups = ArrayList<Group?>()
                            for (groupSnapshot in snapshot.child(FIREBASE_GROUP).getChildren()) {
                                val group: Group? = groupSnapshot.getValue<Group>()
                                groups.add(group)
                            }
                            var pom: Int = 0;
                            for (group in groups) {
                                if (group != null) {
                                    if (group.id == idGroup) {
                                        break;
                                    } else if (pom == groups.size-1) {
                                        exists = false;
                                    }
                                }
                                pom++;
                            }
                        } else
                            exists = false;
                    }
                    var group: Group
                    group = Group("",idGroup)
                    db.child(FIREBASE_GROUP).child(key).setValue(group)
                    userGroupID = key
                    //TODO
                   //createAuthUser()
                }

                override fun onCancelled(error: DatabaseError) {
                    exists = false;
                }
            })

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
        cbGroup = findViewById(R.id.cbGroup)
        groupID = findViewById(R.id.editGroup)
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

    private fun createGroupOptional(){
        if (groupChecked){
            if(groupID.text.isEmpty()) {
                Toast.makeText(this, "Please enter Group ID", Toast.LENGTH_SHORT).show()
            }
            else{
                db.child(FIREBASE_GROUP_IDs).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val groupsIds = mutableListOf<String?>()
                        if (snapshot.exists()) {
                            for (id in snapshot.children) {
                                groupsIds.add(id.getValue<String>())
                            }
                            userGroupID = ""
                            groupsIds.forEachIndexed{ index, item ->
                                if (item == groupID.text.toString()) {
                                    userGroupID = item
                                }
                            }
                            if(userGroupID.isEmpty()){
                                Toast.makeText(this@CreateAccount, "There is no Groups with specified ID", Toast.LENGTH_SHORT).show()
                                return
                            }
                            val group = Group(id = userGroupID, name = "")
                           createAuthUser(group,groupsIds)
                        }
                        else {
                            Toast.makeText(this@CreateAccount, "There is no Groups with specified ID", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
        else{
            generateGroupID()
            db.child(FIREBASE_GROUP_IDs).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val groupsIds = mutableListOf<String?>()
                    if (snapshot.exists()) {
                        var done: Boolean = false
                        while (!done) {
                            run loop@{
                                snapshot.children.forEach { item ->
                                    Log.d(TAG, "onDataChange CHILD: ${item.value.toString()} ")
                                    if (item.value.toString() == userGroupID) {
                                        groupsIds.clear()
                                        generateGroupID()
                                        return@loop
                                    } else {
                                        groupsIds.add(item.value.toString())
                                    }
                                }
                                groupsIds.add(userGroupID)
                                done = true
                            }

                        }
                        val group = Group(id = userGroupID, name = "")
                        createAuthUser(group,groupsIds)
                    } else {
                        val groupsIds = mutableListOf<String?>(userGroupID)
                        val group = Group(id = userGroupID, name = "")
                        createAuthUser(group,groupsIds)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: CANCELED")
                }
            })
        }
    }

    private fun generateGroupID() {
        val key: String = db.child(FIREBASE_GROUP).push().key.toString()
        val charCount: Int = 6
        val random: Int = Random.nextInt(0, key.length - charCount)
        userGroupID = key.substring(random, random + charCount)
        Log.d(TAG, "createGroupOptional: $key")
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

    private fun storeUser(uid: String, gid: String){
        val user = User(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString(),
            gid
        )
        db.child("users").child(uid).setValue(user)
    }

    private fun createAuthUser(group: Group, groupsIds: MutableList<String?>){
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnSuccessListener{ authResult->
            var uid = authResult.user?.uid
            if (uid != null){
                storeUser(uid,userGroupID)
                db.child(FIREBASE_GROUP_IDs).setValue(groupsIds)
                db.child(FIREBASE_GROUP).child(group.id).setValue(group)
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
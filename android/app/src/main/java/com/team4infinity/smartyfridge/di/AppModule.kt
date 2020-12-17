package com.team4infinity.smartyfridge.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Provides
    fun provideFirebaseDatabaseRef(): DatabaseReference{
        return FirebaseDatabase.getInstance().reference
    }
    @Provides
    fun provideFirebaseStorageRef(): StorageReference{
        return FirebaseStorage.getInstance().reference
    }

}
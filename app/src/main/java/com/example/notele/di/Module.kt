package com.example.notele.di

import android.content.Context
import androidx.room.Room
import com.example.notele.db.NoteleDataBase
import com.example.notele.repository.NoteleRepository
import com.example.notele.repository.NoteleRepositoryImpl
import com.example.notele.usecases.AddNotele
import com.example.notele.usecases.DeleteNotele
import com.example.notele.usecases.GetIdNote
import com.example.notele.usecases.GetListUseCase
import com.example.notele.usecases.model.ModelUsesCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun bdRoomModule(@ApplicationContext context: Context): NoteleDataBase {
        return Room.databaseBuilder(
            context.applicationContext, NoteleDataBase::class.java, "notele_db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getRepository(noteleDataBase: NoteleDataBase): NoteleRepository {
        return NoteleRepositoryImpl(noteleDataBase.getDao())
    }


    @Singleton
    @Provides
    fun getUsesCases(repository: NoteleRepository): ModelUsesCases{
        return ModelUsesCases(
            getAllList = GetListUseCase(repository),
            deleteNotele = DeleteNotele(repository),
            addNotele = AddNotele(repository),
            getIdNote = GetIdNote(repository)
        )
    }

}
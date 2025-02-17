package com.example.notele.data.di

import android.content.Context
import androidx.room.Room
import com.example.notele.data.db.NoteleDataBase
import com.example.notele.data.repository.NoteleRepository
import com.example.notele.data.repository.NoteleRepositoryImpl
import com.example.notele.domain.usecases.AddNotele
import com.example.notele.domain.usecases.DeleteNotele
import com.example.notele.domain.usecases.GetIdNote
import com.example.notele.domain.SortNotesUseCase
import com.example.notele.domain.model.ModelUsesCases
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
            sortNotesUseCase = SortNotesUseCase(repository),
            deleteNotele = DeleteNotele(repository),
            addNotele = AddNotele(repository),
            getIdNote = GetIdNote(repository)
        )
    }

}
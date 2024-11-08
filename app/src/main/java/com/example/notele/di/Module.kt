package com.example.notele.di

import android.content.Context
import androidx.room.Room
import com.example.notele.db.NoteleDataBase
import com.example.notele.repository.NoteleRepository
import com.example.notele.repository.NoteleRepositoryImpl
import com.example.notele.usecases.DeleteNotele
import com.example.notele.usecases.GetListUseCase
import com.example.notele.usecases.model.ModelUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun bdRoomModule(context: Context): NoteleDataBase {
        return Room.databaseBuilder(
            context, NoteleDataBase::class.java, "notele_table")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getRepository(noteleDataBase: NoteleDataBase): NoteleRepository{
        return NoteleRepositoryImpl(noteleDataBase.getDao())
    }

    @Singleton
    @Provides
    fun getUsesCases(repository: NoteleRepository): ModelUseCases{
        return ModelUseCases(
            getList = GetListUseCase(repository),
            deleteNotele = DeleteNotele(repository)
        )
    }

}
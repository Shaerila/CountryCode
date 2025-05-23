package com.example.walmartcodingassesment.data.repo

import android.content.Context
import androidx.room.Room
import com.example.walmartcodingassesment.data.local.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CountryDatabase {
        return Room.databaseBuilder(
            context,
            CountryDatabase::class.java,
            "country_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCountryDao(database: CountryDatabase): CountryDao {
        return database.countryDao()
    }
}
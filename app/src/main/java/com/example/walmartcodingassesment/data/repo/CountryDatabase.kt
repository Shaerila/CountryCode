package com.example.walmartcodingassesment.data.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.walmartcodingassesment.data.local.dao.CountryDao
import com.example.walmartcodingassesment.domain.model.CountryItem

@Database(entities = [CountryItem::class], version = 2)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}
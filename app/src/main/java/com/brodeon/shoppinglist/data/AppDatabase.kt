package com.brodeon.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Tworzy bazę danych przechowującą listy zakupów za pierwszym uruchomieniem aplikacji. Klasa ta jest Singletonem
 */
@Database(entities = [ShoppingList::class, ShoppingListItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun shoppingListItemDao(): ShoppingListItemDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        /**
         * Zwraca instancję klasy AppDatabase
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        /**
         * Tworzy bazę danych
         */
        private fun buildDatabase(context: Context): AppDatabase {
             return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "shopping_lists").build()
        }
    }
}
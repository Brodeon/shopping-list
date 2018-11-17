package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData

class ShoppingListItemRepository(application: Application) {

    private val database: AppDatabase = AppDatabase.getInstance(application)
    private val shoppingListItemDao: ShoppingListItemDao = database.shoppingListItemDao()

    fun shoppingListItems(listId: Int): LiveData<List<ShoppingListItem>> {
        return shoppingListItemDao.getListItems(listId)
    }
}
package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

class ShoppingListItemRepository(application: Application) {

    private val database: AppDatabase = AppDatabase.getInstance(application)
    private val shoppingListItemDao: ShoppingListItemDao = database.shoppingListItemDao()

    fun shoppingListItems(listId: Int): LiveData<List<ShoppingListItem>> {
        return shoppingListItemDao.getListItems(listId)
    }

    fun insertShoppingListItem(shoppingListItem: ShoppingListItem) {
        doAsync {
            shoppingListItemDao.insert(shoppingListItem)
        }
    }

    fun updateIsBoughtItem(shoppingListItem: ShoppingListItem) {
        doAsync {
            shoppingListItemDao.updateIsBoughtItem(shoppingListItem.isBought, shoppingListItem.itemId)
        }
    }

    fun deleteItem(shoppingListItem: ShoppingListItem) {
        doAsync {
            shoppingListItemDao.deleteItem(shoppingListItem)
        }
    }
}
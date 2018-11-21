package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

class ShoppingListRepository(application: Application){

    private val database: AppDatabase = AppDatabase.getInstance(application)
    private val shoppingListDao: ShoppingListDao = database.shoppingListDao()
    private val allShoppingLists: LiveData<List<ShoppingList>> = shoppingListDao.getAllShoppingLists()

    fun allShoppingLists(): LiveData<List<ShoppingList>> {
        return this.allShoppingLists
    }

    fun insertShoppingList(shoppingList: ShoppingList) {
        doAsync {
            shoppingListDao.insert(shoppingList)
        }
    }

    fun deleteList(shoppingList: ShoppingList) {
        doAsync {
            shoppingListDao.deleteList(shoppingList)
        }
    }

    fun updateListName(listName: String, listId: Int) {
        doAsync {
            shoppingListDao.updateListName(listName, listId)
        }
    }

}
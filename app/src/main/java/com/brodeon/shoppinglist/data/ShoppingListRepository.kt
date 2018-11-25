package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

/**
 * Repozytorium zawierające metody obsługi listy zakupów. Metody te działają asynchornicznie
 */
class ShoppingListRepository(application: Application){

    private val database: AppDatabase = AppDatabase.getInstance(application)
    private val shoppingListDao: ShoppingListDao = database.shoppingListDao()
    private val allShoppingLists: LiveData<List<ShoppingList>> = shoppingListDao.getAllShoppingLists()

    /**
     * Zwraca listy zakupów
     */
    fun allShoppingLists(): LiveData<List<ShoppingList>> {
        return this.allShoppingLists
    }

    /**
     * Dodaje do bazy nową listę zakupów
     */
    fun insertShoppingList(shoppingList: ShoppingList) {
        doAsync {
            shoppingListDao.insert(shoppingList)
        }
    }

    /**
     * Usuwa listę zakupów z bazy danych
     */
    fun deleteList(shoppingList: ShoppingList) {
        doAsync {
            shoppingListDao.deleteList(shoppingList)
        }
    }

    /**
     * Aktualizuje nazwę listy zakupów
     */
    fun updateListName(listName: String, listId: Int) {
        doAsync {
            shoppingListDao.updateListName(listName, listId)
        }
    }

}
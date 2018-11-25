package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

/**
 * Repozytorium zawierające metody obsługi elemetów listy zakupów. Metody te działają asynchornicznie
 */
class ShoppingListItemRepository(application: Application) {

    private val database: AppDatabase = AppDatabase.getInstance(application)
    private val shoppingListItemDao: ShoppingListItemDao = database.shoppingListItemDao()

    /**
     * Zwraca listę zakupów na podstawie id listy
     */
    fun shoppingListItems(listId: Int): LiveData<List<ShoppingListItem>> {
        return shoppingListItemDao.getListItems(listId)
    }

    /**
     * Dodaje do bazy nowy element listy zakupów
     */
    fun insertShoppingListItem(shoppingListItem: ShoppingListItem) {
        doAsync {
            shoppingListItemDao.insert(shoppingListItem)
        }
    }

    /**
     * Aktualizuje pole isBought elementu listy zakupów
     */
    fun updateIsBoughtItem(shoppingListItem: ShoppingListItem) {
        doAsync {
            shoppingListItemDao.updateIsBoughtItem(shoppingListItem.isBought, shoppingListItem.itemId)
        }
    }

    /**
     * Usuwa element listy zakupów z bazy danych
     */
    fun deleteItem(shoppingListItem: ShoppingListItem) {
        doAsync {
            shoppingListItemDao.deleteItem(shoppingListItem)
        }
    }

    /**
     * Aktualizuje nazwę elementu listy zakupów
     */
    fun updateItemText(itemText: String, itemId: Int) {
        doAsync {
            shoppingListItemDao.updateItemText(itemText, itemId)
        }
    }
}
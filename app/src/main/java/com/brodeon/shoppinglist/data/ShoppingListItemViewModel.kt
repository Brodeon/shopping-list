package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 * Klasa zawierająca listę zakupów oraz metody obsługi elementów listy zakupów
 */
class ShoppingListItemViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Lista zakupów
     */
    private val repository: ShoppingListItemRepository = ShoppingListItemRepository(application)

    /**
     * Zwraca element listy zakupów na podstawie id listy
     */
    fun shoppingListItems(listId: Int): LiveData<List<ShoppingListItem>> {
        return repository.shoppingListItems(listId)
    }

    /**
     * Dodaje do bazy nowy element listy zakupów
     */
    fun insertShoppingListItem(shoppingListItem: ShoppingListItem) {
        repository.insertShoppingListItem(shoppingListItem)
    }

    /**
     * Aktualizuje pole isBought elementu listy zakupów
     */
    fun updateIsBoughtItem(shoppingListItem: ShoppingListItem) {
        repository.updateIsBoughtItem(shoppingListItem)
    }

    /**
     * Usuwa element listy zakupów z bazy danych
     */
    fun deleteItem(shoppingListItem: ShoppingListItem) {
        repository.deleteItem(shoppingListItem)
    }

    /**
     * Aktualizuje nazwę elementu listy zakupów
     */
    fun updateItemText(itemText: String, itemId: Int) {
        repository.updateItemText(itemText, itemId)
    }
}
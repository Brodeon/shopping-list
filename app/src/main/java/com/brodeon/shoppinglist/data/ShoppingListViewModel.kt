package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 * Klasa zawierająca listę list zakupów oraz metody obsługi list zakupów
 */
class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShoppingListRepository(application)

    /**
     * Lista list zakupów
     */
    private val allShoppingLists: LiveData<List<ShoppingList>> = repository.allShoppingLists()

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
        repository.insertShoppingList(shoppingList)
    }

    /**
     * Usuwa listę zakupów z bazy danych
     */
    fun deleteList(shoppingList: ShoppingList) {
        repository.deleteList(shoppingList)
    }

    /**
     * Aktualizuje nazwę listy zakupów
     */
    fun updateListName(listName: String, listId: Int) {
        repository.updateListName(listName, listId)
    }
}
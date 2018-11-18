package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShoppingListRepository(application)
    private val allShoppingLists: LiveData<List<ShoppingList>> = repository.allShoppingLists()

    fun allShoppingLists(): LiveData<List<ShoppingList>> {
        return this.allShoppingLists
    }

    fun insertShoppingList(shoppingList: ShoppingList) {
        repository.insertShoppingList(shoppingList)
    }

    fun deleteList(shoppingList: ShoppingList) {
        repository.deleteList(shoppingList)
    }
}
package com.brodeon.shoppinglist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ShoppingListItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShoppingListItemRepository = ShoppingListItemRepository(application)

    fun shoppingListItems(listId: Int): LiveData<List<ShoppingListItem>> {
        return repository.shoppingListItems(listId)
    }

    fun insertShoppingListItem(shoppingListItem: ShoppingListItem) {
        repository.insertShoppingListItem(shoppingListItem)
    }
}
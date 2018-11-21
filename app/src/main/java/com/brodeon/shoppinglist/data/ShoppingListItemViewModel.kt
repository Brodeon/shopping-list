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

    fun updateIsBoughtItem(shoppingListItem: ShoppingListItem) {
        repository.updateIsBoughtItem(shoppingListItem)
    }

    fun deleteItem(shoppingListItem: ShoppingListItem) {
        repository.deleteItem(shoppingListItem)
    }

    fun updateItemText(itemText: String, itemId: Int) {
        repository.updateItemText(itemText, itemId)
    }
}
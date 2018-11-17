package com.brodeon.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingListItemDao {

    @Insert
    fun insert(shoppingListItem: ShoppingListItem)

    @Query("DELETE FROM shopping_list_items")
    fun deleteAll()

    @Delete
    fun deleteItem(shoppingListItem: ShoppingListItem)

    @Query("DELETE FROM shopping_list_items WHERE itemId = :itemId")
    fun deleteItem(itemId: Int)

    @Query("SELECT * FROM shopping_list_items WHERE shoppingListId = :shoppingListId")
    fun getListItems(shoppingListId: Int): LiveData<List<ShoppingListItem>>

    @Query("SELECT * FROM shopping_list_items WHERE itemId = :itemId")
    fun getItem(itemId: Int): ShoppingListItem
}
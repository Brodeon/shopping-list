package com.brodeon.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Interfejs zawierający metody obsługi tabeli list zakupów
 */
@Dao
interface ShoppingListDao {

    @Insert
    fun insert(shoppingList: ShoppingList)

    @Query("DELETE FROM shopping_lists")
    fun deleteAll()

    @Query("DELETE FROM shopping_lists WHERE listId = :listId")
    fun deleteList(listId: Int)

    @Delete
    fun deleteList(shoppingList: ShoppingList)

    @Query("SELECT * FROM shopping_lists ORDER BY listId ASC")
    fun getAllShoppingLists(): LiveData<List<ShoppingList>>

    @Query("SELECT * FROM shopping_lists WHERE listId = :listId")
    fun getShoppingList(listId: Int): ShoppingList

    @Query("UPDATE shopping_lists SET listName = :listName WHERE listId = :listId")
    fun updateListName(listName: String, listId: Int)

}
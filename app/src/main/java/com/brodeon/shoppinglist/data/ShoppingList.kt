package com.brodeon.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Klasa reprezentująca listę zakupów(nazwę oraz Id)
 */
@Entity(tableName = "shopping_lists")
data class ShoppingList(
    @ColumnInfo(name = "listName") var listName: String
){
    @PrimaryKey(autoGenerate = true) var listId: Int = 0
}
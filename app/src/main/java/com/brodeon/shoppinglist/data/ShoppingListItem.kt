package com.brodeon.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Klasa reprezentująca elementy listy zakupów(nazwa, id, id listy zakupów, czy kupione). Zawiera również relację z listą zakupów.
 * Gdy lista zakupów jest usuwana, usuwane są również elementy tej listy automatycznie
 */
@Entity(tableName = "shopping_list_items",
    foreignKeys = arrayOf(
        ForeignKey(entity = ShoppingList::class,
            parentColumns = arrayOf("listId"),
            childColumns = arrayOf("shoppingListId"),
            onDelete = ForeignKey.CASCADE)
    ))
data class ShoppingListItem(
    @ColumnInfo(name = "itemText")
    var itemText: String,
    @ColumnInfo(name = "isBought")
    var isBought: Boolean,
    @ColumnInfo(name = "shoppingListId")
    var shoppingListId: Int
){
    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0
}


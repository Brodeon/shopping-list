package com.brodeon.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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


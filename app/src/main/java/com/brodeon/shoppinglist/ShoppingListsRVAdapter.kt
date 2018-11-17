package com.brodeon.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brodeon.shoppinglist.data.ShoppingList
import kotlinx.android.synthetic.main.shopping_list_layout.view.*

class ShoppingListsRVAdapter(private var listener: OnListLongClicked)
    : RecyclerView.Adapter<ShoppingListsRVAdapter.ShoppingListsViewHolder>() {

    private var shoppingLists: List<ShoppingList>? = null

    interface OnListLongClicked {
        fun onListLongClicked(position: Int, shoppingList: ShoppingList)
        fun onListClicked(shoppingList: ShoppingList, view: View)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShoppingListsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.shopping_list_layout, viewGroup, false)
        return ShoppingListsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shoppingLists?.size ?: 0
    }

    fun updateList(shoppingLists: List<ShoppingList>) {
        this.shoppingLists = shoppingLists
        notifyDataSetChanged()
    }

    fun updateListElement(position: Int) {
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(viewHolder: ShoppingListsViewHolder, position: Int) {
        shoppingLists?.let {
            val shoppingList = it[position]

            viewHolder.listName.text = shoppingList.listName

            viewHolder.view.setOnLongClickListener {
                listener.onListLongClicked(position, shoppingList)
                true
            }

            viewHolder.view.setOnClickListener {
                listener.onListClicked(shoppingList, it)
            }
        }

    }

    inner class ShoppingListsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var listName = itemView.listname_tv!!
        var view = itemView
    }
}
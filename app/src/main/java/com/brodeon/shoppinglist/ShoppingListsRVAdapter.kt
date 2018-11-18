package com.brodeon.shoppinglist

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brodeon.shoppinglist.data.ShoppingList
import kotlinx.android.synthetic.main.shopping_list_layout.view.*

class ShoppingListsRVAdapter(private var listener: OnListLongClicked)
    : RecyclerView.Adapter<ShoppingListsRVAdapter.ShoppingListsViewHolder>() {

    private var shoppingLists: List<ShoppingList>? = null
    var onLongShoppingList: ShoppingList? = null

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
                onLongShoppingList = shoppingList
                false
            }

            viewHolder.view.setOnClickListener {
                listener.onListClicked(shoppingList, it)
            }
        }

    }

    inner class ShoppingListsViewHolder : androidx.recyclerview.widget.RecyclerView.ViewHolder, View.OnCreateContextMenuListener {

        val listName: TextView
        val view: View

        constructor(itemView: View) : super(itemView) {
            listName = itemView.listname_tv!!
            view = itemView

            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, R.id.edit_list_cvi, Menu.NONE, "Edit")
            menu?.add(Menu.NONE, R.id.delete_list_cvi, Menu.NONE, "Delete")
        }
    }
}
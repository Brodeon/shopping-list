package com.brodeon.shoppinglist

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brodeon.shoppinglist.data.ShoppingList
import kotlinx.android.synthetic.main.shopping_list_layout.view.*

/**
 * Adapter obsługujący RecycleView zawierający listy list zakupów
 */
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

    /**
     * Metoda aktualizująca RecycleView, oraz obiekt w liście zakupów
     */
    fun updateList(shoppingLists: List<ShoppingList>) {
        this.shoppingLists = shoppingLists
        notifyDataSetChanged()
    }


    /**
     * Metoda aktualizująca RecycleView
     */
    fun updateListElement() {
        notifyDataSetChanged()
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

    inner class ShoppingListsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        val listName: TextView = itemView.listname_tv!!
        val view: View = itemView

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        /**
         * Tworzy ContextMenu który uzyskujemy poprzez dłuższe przyciśnięcie element listy
         */
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, R.id.edit_list_cvi, Menu.NONE, "Edit list")
            menu?.add(Menu.NONE, R.id.delete_list_cvi, Menu.NONE, "Delete list")
        }
    }
}
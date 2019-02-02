package com.brodeon.shoppinglist

import android.animation.ObjectAnimator
import android.view.*
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brodeon.shoppinglist.data.ShoppingListItem
import kotlinx.android.synthetic.main.shopping_list_item.view.*

/**
 * Adapter dla RecycleView zawierający listę zakupów
 */
class ShoppingItemsRVAdapter(private var listener: OnItemClicked)
    : RecyclerView.Adapter<ShoppingItemsRVAdapter.ItemsViewHolder>(){

    private var itemsList: List<ShoppingListItem>? = null
    var onLongShoppingListItem: ShoppingListItem? = null

    interface OnItemClicked {
        fun onItemLongClicked(position: Int, shoppingListItem: ShoppingListItem)
        fun onCheckboxChanged(position: Int, shoppingListItem: ShoppingListItem, isChecked: Boolean)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.shopping_list_item, viewGroup, false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList?.size ?: 0
    }

    /**
     * Metoda aktualizująca RecycleView, oraz obiekt w liście zakupów
     */
    fun updateList(itemList: List<ShoppingListItem>) {
        this.itemsList = itemList
        notifyDataSetChanged()
    }

    /**
     * Metoda aktualizująca RecycleView
     */
    fun updateItem() {
        notifyDataSetChanged()
    }

    /**
     * Zwraca obiekt z odpowiedniej pozycji listy obiektów
     */
    fun itemFromPosition(position: Int): ShoppingListItem? {
        return itemsList?.get(position)
    }

    override fun onBindViewHolder(viewHolder: ItemsViewHolder, position: Int) {
        itemsList?.let {
            val item = it[position]
            val imageResourceId: Int = if (item.isBought) R.drawable.checkbox_checked else R.drawable.checkbox_unchecked
            viewHolder.isBoughtCheckBox.setImageResource(imageResourceId)

            viewHolder.view.setOnClickListener {
                listener.onCheckboxChanged(position, item, item.isBought)
            }

            viewHolder.itemDescription.text = item.itemText
            viewHolder.view.setOnLongClickListener {
                onLongShoppingListItem = item
                false
            }
        }
    }

    inner class ItemsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        var view: View = itemView
        var itemDescription: TextView = itemView.item_description_tv!!
        var isBoughtCheckBox: ImageView = itemView.checkbox_image!!
        var foregroundView: View = itemView.findViewById(R.id.foreground_view)

        init {
            view.setOnCreateContextMenuListener(this)
        }

        /**
         * Tworzy ContextMenu który uzyskujemy poprzez dłuższe przyciśnięcie element listy
         */
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, R.id.edit_item_cvi, Menu.NONE, "Edit item")
            menu?.add(Menu.NONE, R.id.delete_item_cvi, Menu.NONE, "Delete item")
        }
    }
}
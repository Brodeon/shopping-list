package com.brodeon.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brodeon.shoppinglist.data.ShoppingListItem
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ShoppingItemsRVAdapter(private var listener: OnItemLongClick)
    : RecyclerView.Adapter<ShoppingItemsRVAdapter.ItemsViewHolder>(){

    private var itemsList: List<ShoppingListItem>? = null

    interface OnItemLongClick {
        fun onItemLongClicked(position: Int, shoppingListItem: ShoppingListItem)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.shopping_list_item, viewGroup, false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList?.size ?: 0
    }

    fun updateList(itemList: List<ShoppingListItem>) {
        this.itemsList = itemList
        notifyDataSetChanged()
    }

    fun updateListElement(position: Int) {
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(viewHolder: ItemsViewHolder, position: Int) {
        itemsList?.let {
            val item = it[position]

            viewHolder.isBoughtCheckBox.isChecked = item.isBought
            viewHolder.itemDescription.text = item.itemText

            viewHolder.view.setOnLongClickListener {
                listener.onItemLongClicked(position, item)
                true
            }
        }
    }

    inner class ItemsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var view = itemView
        var itemDescription = itemView.item_description_tv!!
        var isBoughtCheckBox = itemView.is_bought_cb!!
    }
}
package com.brodeon.shoppinglist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brodeon.shoppinglist.data.ShoppingListItem
import com.brodeon.shoppinglist.data.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shopping_items_list.*

class ShoppingItemsListFragment : Fragment(), ShoppingItemsRVAdapter.OnItemLongClick {

    private lateinit var shoppingItemsAdapter: ShoppingItemsRVAdapter
    private lateinit var itemsViewModel: ShoppingListItemViewModel

    companion object {
        const val LIST_ID: String = "listId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_items_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shoppingItemsRV = rv_shopping_items_list
        shoppingItemsAdapter = ShoppingItemsRVAdapter(this)

        val listId = arguments?.getInt(LIST_ID)

        shoppingItemsRV.adapter = shoppingItemsAdapter
        shoppingItemsRV.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        itemsViewModel = ViewModelProviders.of(this).get(ShoppingListItemViewModel::class.java)
        itemsViewModel.shoppingListItems(listId!!).observe(this, Observer {
            shoppingItemsAdapter.updateList(it!!)
        })

        //TODO fab transition away the screen
    }

    override fun onItemLongClicked(position: Int, shoppingListItem: ShoppingListItem) {
        print(position)
    }
}

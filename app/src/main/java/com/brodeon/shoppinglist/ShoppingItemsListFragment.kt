package com.brodeon.shoppinglist


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brodeon.shoppinglist.AddEditDialog.Companion.ADD_ITEM_DIALOG_ID
import com.brodeon.shoppinglist.data.ShoppingListItem
import com.brodeon.shoppinglist.data.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shopping_items_list.*

class ShoppingItemsListFragment : Fragment(), ShoppingItemsRVAdapter.OnItemLongClick, AddEditDialog.OnDialogResponse {

    private lateinit var shoppingItemsAdapter: ShoppingItemsRVAdapter
    private lateinit var itemsViewModel: ShoppingListItemViewModel
    private var listId: Int? = null

    companion object {
        const val LIST_ID: String = "listId"
        const val LIST_NAME: String = "listName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        this.listId = arguments?.getInt(LIST_ID)
        val listName = arguments?.getString(LIST_NAME)

        (activity as AppCompatActivity).supportActionBar?.title = listName
        return inflater.inflate(R.layout.fragment_shopping_items_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shoppingItemsRV = rv_shopping_items_list
        shoppingItemsAdapter = ShoppingItemsRVAdapter(this)

        shoppingItemsRV.adapter = shoppingItemsAdapter
        shoppingItemsRV.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        itemsViewModel = ViewModelProviders.of(this).get(ShoppingListItemViewModel::class.java)
        itemsViewModel.shoppingListItems(listId!!).observe(this, Observer {
            shoppingItemsAdapter.updateList(it!!)
        })

        hideFab()
        //TODO fab transition away the screen
    }

    private fun hideFab() {
        val fab = activity?.fab
        if (fab?.tag == FabState.VISIBLE) {
            fab.animate().translationYBy(250f).duration = 250
            fab.tag = FabState.HIDDEN
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_items_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.add_item_menu_item -> {
                val addEditDialog = AddEditDialog()

                val bundle = Bundle()
                bundle.putInt(AddEditDialog.DIALOG_ID, AddEditDialog.ADD_ITEM_DIALOG_ID)
                addEditDialog.arguments = bundle

                addEditDialog.attachFragment(this)
                addEditDialog.show(activity?.supportFragmentManager, null)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemLongClicked(position: Int, shoppingListItem: ShoppingListItem) {
        print(position)
    }

    override fun onPositiveClicked(dialogId: Int?, bundle: Bundle?) {
        when(dialogId) {
            ADD_ITEM_DIALOG_ID -> {
                val itemName = bundle?.getString(AddEditDialog.ITEM_NAME_ADD_DIALOG)
                Log.d("ItemsFragment", "item name = $itemName")
                itemsViewModel.insertShoppingListItem(ShoppingListItem(itemName!!, false, listId!!))
            }
        }
    }

    override fun onNegaiveClicked(dialogId: Int?, bundle: Bundle?) {
        when(dialogId) {
            ADD_ITEM_DIALOG_ID -> {
                //do nothing
            }
        }
    }
}

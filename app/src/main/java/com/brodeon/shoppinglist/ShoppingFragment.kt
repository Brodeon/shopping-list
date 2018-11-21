package com.brodeon.shoppinglist


import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.brodeon.shoppinglist.AddEditDialog.Companion.ADD_LIST_DIALOG_ID
import com.brodeon.shoppinglist.AddEditDialog.Companion.EDIT_LIST_DIALOG_ID
import com.brodeon.shoppinglist.data.ShoppingList
import com.brodeon.shoppinglist.data.ShoppingListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shopping.*

class ShoppingFragment : Fragment(),  ShoppingListsRVAdapter.OnListLongClicked, AddEditDialog.OnDialogResponse {

    private lateinit var shoppingListAdapter: ShoppingListsRVAdapter
    private lateinit var listsViewModel: ShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.your_shopping_lists)

        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        shoppingListAdapter.onLongShoppingList?.let {
            when(item?.itemId) {
                R.id.edit_list_cvi -> {
                    Log.d("ShoppingFr", "onContextItemSelected: onEditClicked, list name = ${it.listName}")

                    val addEditDialog = AddEditDialog()

                    val bundle = Bundle()
                    bundle.putInt(AddEditDialog.DIALOG_ID, AddEditDialog.EDIT_LIST_DIALOG_ID)
                    bundle.putString(AddEditDialog.DIALOG_MESSAGE, getString(R.string.dialog_message_edit_list))
                    bundle.putString(AddEditDialog.EDITTEXT_HINT, getString(R.string.edittext_list_hint))
                    bundle.putString(AddEditDialog.ELEMENT_STRING, it.listName)
                    bundle.putString(AddEditDialog.POSITIVE_BTN_STRING, getString(R.string.edit))
                    bundle.putInt(AddEditDialog.ELEMENT_EDIT_ID, it.listId)

                    addEditDialog.arguments = bundle
                    addEditDialog.attachFragment(this)

                    addEditDialog.show(activity?.supportFragmentManager, null)
                }
                R.id.delete_list_cvi -> {
                    Log.d("ShoppingFr", "onContextItemSelected: onDeleteClicked, list name = ${it.listName}")
                    listsViewModel.deleteList(it)
                }
            }
            return super.onContextItemSelected(item)
        }
        return super.onContextItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecycleView()
        setViewModels()
        setActivityFabOnClickListener()

        showFab()
    }

    private fun configureRecycleView() {
        val shoppingListRV = rv_shopping_lists
        shoppingListAdapter = ShoppingListsRVAdapter(this)

        if (resources.configuration.orientation == ORIENTATION_PORTRAIT) {
            shoppingListRV.layoutManager = GridLayoutManager(context, 2)
        } else {
            shoppingListRV.layoutManager = GridLayoutManager(context, 3)
        }

        shoppingListRV.adapter = shoppingListAdapter
    }

    private fun setActivityFabOnClickListener() {
        activity?.fab?.setOnClickListener {
            val addEditDialog = AddEditDialog()

            val bundle = Bundle()
            bundle.putInt(AddEditDialog.DIALOG_ID, AddEditDialog.ADD_LIST_DIALOG_ID)
            bundle.putString(AddEditDialog.DIALOG_MESSAGE, getString(R.string.dialog_message_list))
            bundle.putString(AddEditDialog.EDITTEXT_HINT, getString(R.string.edittext_list_hint))
            bundle.putString(AddEditDialog.POSITIVE_BTN_STRING, getString(R.string.add))


            addEditDialog.arguments = bundle
            addEditDialog.attachFragment(this)

            addEditDialog.show(activity?.supportFragmentManager, null)
        }
    }

    private fun showFab() {
        val fab = activity?.fab
        if (fab?.tag == FabState.HIDDEN) {
            fab.animate().translationYBy(-250f).duration = 250
            fab.tag = FabState.VISIBLE
        }
    }

    private fun setViewModels() {
        listsViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
        listsViewModel.allShoppingLists().observe(this, Observer {
            shoppingListAdapter.updateList(it!!)
        })
    }

    override fun onListLongClicked(position: Int, shoppingList: ShoppingList) {
        print(position)
    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        inflater?.inflate(R.menu.shopping_lists_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onListClicked(shoppingList: ShoppingList, view: View) {
        val bundle = Bundle()
        bundle.putInt(ShoppingItemsListFragment.LIST_ID, shoppingList.listId)
        bundle.putString(ShoppingItemsListFragment.LIST_NAME, shoppingList.listName)

        Navigation.findNavController(view).navigate(R.id.toItems, bundle)
    }

    override fun onPositiveClicked(dialogId: Int?, bundle: Bundle?) {
        Log.d("ShoppingFr", "onPositiveClicked called. dialogId = $dialogId")
        when(dialogId) {
            ADD_LIST_DIALOG_ID -> {
                val listName = bundle?.getString(AddEditDialog.ELEMENT_STRING)
                Log.d("ShoppingFr", "shopping list name = $listName")
                listsViewModel.insertShoppingList(ShoppingList(listName!!))
            }

            EDIT_LIST_DIALOG_ID -> {
                val listName = bundle?.getString(AddEditDialog.ELEMENT_STRING)
                val listId = bundle?.getInt(AddEditDialog.ELEMENT_EDIT_ID)
                listsViewModel.updateListName(listName!!, listId!!)
                shoppingListAdapter.onLongShoppingList?.also { it.listName = listName }
                shoppingListAdapter.updateListElement()
            }
        }
    }

    override fun onNegativeClicked(dialogId: Int?, bundle: Bundle?) {
        when(dialogId) {
            ADD_LIST_DIALOG_ID -> {
                //nic nie rob
            }
            EDIT_LIST_DIALOG_ID -> {
                //nic nie rob
            }
        }
    }
}

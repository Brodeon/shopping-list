package com.brodeon.shoppinglist


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.brodeon.shoppinglist.AddEditDialog.Companion.ADD_ITEM_DIALOG_ID
import com.brodeon.shoppinglist.AddEditDialog.Companion.EDIT_ITEM_DIALOG_ID
import com.brodeon.shoppinglist.data.ShoppingListItem
import com.brodeon.shoppinglist.data.ShoppingListItemViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shopping_items_list.*

/**
 * Fragment zawierający listę zakupów
 */
class ShoppingItemsListFragment : Fragment(), ShoppingItemsRVAdapter.OnItemClicked, AddEditDialog.OnDialogResponse, ItemsItemHelper.OnSwipeListener {

    private lateinit var shoppingItemsAdapter: ShoppingItemsRVAdapter
    private lateinit var itemsViewModel: ShoppingListItemViewModel
    private var listId: Int? = null

    /**
     * Zawiera zmienne które służą jako wartość klucza w Bundle
     */
    companion object {
        const val LIST_ID: String = "listId"
        const val LIST_NAME: String = "listName"
    }

    /**
     * Pobiera dane z Bundle na temat id listy z której powinien pobrać listę zakupów
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar

        this.listId = arguments?.getInt(LIST_ID)
        val listName = arguments?.getString(LIST_NAME)

        supportActionBar?.title = listName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return inflater.inflate(R.layout.fragment_shopping_items_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecycleView()
        setViewModels()
        hideFab()
    }

    /**
     * Obługuje kliknięcie w ContextMenu, które uzyskujemy poprzez dłuższe kliknięcie elementu w recycleView
     */
    override fun onContextItemSelected(item: MenuItem?): Boolean {
        shoppingItemsAdapter.onLongShoppingListItem?.let {
            when(item?.itemId) {

                /**
                 * Kliknięte zostało "Edit"
                 */
                R.id.edit_item_cvi -> {
                    Log.d("ItemsFragment", "onContextItemSelected: onEditClicked, list name = ${it.itemText}")

                    val addEditDialog = AddEditDialog()

                    val bundle = Bundle()
                    bundle.putInt(AddEditDialog.DIALOG_ID, AddEditDialog.EDIT_ITEM_DIALOG_ID)
                    bundle.putString(AddEditDialog.DIALOG_MESSAGE, getString(R.string.dialog_message_edit_item))
                    bundle.putString(AddEditDialog.EDITTEXT_HINT, getString(R.string.edittext_item_hint))
                    bundle.putString(AddEditDialog.ELEMENT_STRING, it.itemText)
                    bundle.putString(AddEditDialog.POSITIVE_BTN_STRING, getString(R.string.edit))
                    bundle.putInt(AddEditDialog.ELEMENT_EDIT_ID, it.itemId)

                    addEditDialog.arguments = bundle
                    addEditDialog.attachFragment(this)

                    addEditDialog.show(activity?.supportFragmentManager, null)
                }

                /**
                 * Kliknięte zostało "delete"
                 */
                R.id.delete_item_cvi -> {
                    Log.d("ItemsFragment", "onContextItemSelected: onDeleteClicked, list name = ${it.itemText}")
                    itemsViewModel.deleteItem(it)
                }
            }
            return super.onContextItemSelected(item)

        }

        return super.onContextItemSelected(item)
    }

    /**
     * Konfiguruje RecycleView który wyświetla listę zakupów. Dodaje do RecycleView nasz itemsItemHelper który pozwala na
     * swipowanie elementu listy
     */
    private fun configureRecycleView() {
        val shoppingItemsRV = rv_shopping_items_list
        shoppingItemsAdapter = ShoppingItemsRVAdapter(this)

        shoppingItemsRV.adapter = shoppingItemsAdapter
        shoppingItemsRV.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        val itemsItemHelper = ItemsItemHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemsItemHelper).attachToRecyclerView(shoppingItemsRV)
    }

    /**
     * Wywołana gdy użytkownik wykonał swipe elementu listy
     */
    override fun onSwipe(position: Int) {
            shoppingItemsAdapter.itemFromPosition(position)?.let {
            itemsViewModel.deleteItem(it)
        }
    }

    /**
     * Ustawia ViewModel, pobiera odpowienie elementy listy zakupów oraz obserwuję listę zakupów i informuje o zmianach
     * tej listy
     */
    private fun setViewModels() {
        itemsViewModel = ViewModelProviders.of(this).get(ShoppingListItemViewModel::class.java)
        itemsViewModel.shoppingListItems(listId!!).observe(this, Observer {
            shoppingItemsAdapter.updateList(it!!)
        })
    }

    /**
     * Wykonuje animację chowania FloatingActionButton
     */
    private fun hideFab() {
        val fab = activity?.fab
        if (fab?.tag == FabState.VISIBLE) {
            fab.animate().translationYBy(250f).duration = 250
            fab.tag = FabState.HIDDEN
        }
    }

    /**
     * Tworzy OptionsMenu
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_items_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Wywołuje odpowiedni kod po naciśnięciu home button, lub przycisku dodwania
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {

            /**
             * Naciśnięty został przycisk dodania nowego elementu
             */
            R.id.add_item_menu_item -> {
                val addEditDialog = AddEditDialog()

                val bundle = Bundle()
                bundle.putInt(AddEditDialog.DIALOG_ID, AddEditDialog.ADD_ITEM_DIALOG_ID)
                bundle.putString(AddEditDialog.DIALOG_MESSAGE, getString(R.string.dialog_message_item))
                bundle.putString(AddEditDialog.EDITTEXT_HINT, getString(R.string.edittext_item_hint))
                bundle.putString(AddEditDialog.POSITIVE_BTN_STRING, getString(R.string.add))

                addEditDialog.arguments = bundle

                addEditDialog.attachFragment(this)
                addEditDialog.show(activity?.supportFragmentManager, null)
            }

            /**
             * Naciśnięty został home button
             */
            android.R.id.home -> {
                val navgationFragmentView: View? = activity?.findViewById(R.id.nav_host_fragment)
                Navigation.findNavController(navgationFragmentView!!).navigateUp()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Nic nie robi
     */
    override fun onItemLongClicked(position: Int, shoppingListItem: ShoppingListItem) {
        print(position)
    }

    /**
     * Wywołane gdy został naciśnięty checkbox
     */
    override fun onCheckboxChanged(position: Int, shoppingListItem: ShoppingListItem, isChecked: Boolean) {
        Log.d("ItemsFragment", "onCheckboxChanged: ${shoppingListItem.itemText}: $isChecked")
        shoppingListItem.isBought = !isChecked
        itemsViewModel.updateIsBoughtItem(shoppingListItem)
    }

    /**
     * Zajmuje się obsługą naciśniętego positiveButton w Dialogu
     */
    override fun onPositiveClicked(dialogId: Int?, bundle: Bundle?) {
        when(dialogId) {
            ADD_ITEM_DIALOG_ID -> {
                val itemName = bundle?.getString(AddEditDialog.ELEMENT_STRING)
                Log.d("ItemsFragment", "item name = $itemName")
                itemsViewModel.insertShoppingListItem(ShoppingListItem(itemName!!, false, listId!!))
            }

            EDIT_ITEM_DIALOG_ID -> {
                val itemText = bundle?.getString(AddEditDialog.ELEMENT_STRING)
                val itemId = bundle?.getInt(AddEditDialog.ELEMENT_EDIT_ID)
                itemsViewModel.updateItemText(itemText!!, itemId!!)
                shoppingItemsAdapter.onLongShoppingListItem?.also { it.itemText = itemText }
                shoppingItemsAdapter.updateItem()
            }
        }
    }

    /**
     * Zajmuje się obsługą naciśniętego negativeButton w Dialogu
     */
    override fun onNegativeClicked(dialogId: Int?, bundle: Bundle?) {
        when(dialogId) {
            ADD_ITEM_DIALOG_ID -> {
                //nic nie rob
            }

            EDIT_ITEM_DIALOG_ID -> {
                //nic nie rob
            }
        }
    }
}

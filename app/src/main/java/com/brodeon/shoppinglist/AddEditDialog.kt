package com.brodeon.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddEditDialog : DialogFragment() {

    private lateinit var onDialogResponseListener: OnDialogResponse

    companion object {
        const val ADD_LIST_DIALOG_ID = 1
        const val ADD_ITEM_DIALOG_ID = 2

        const val DIALOG_ID = "dialogId"
        const val LIST_NAME_ADD_DIALOG = "listName"
        const val ITEM_NAME_ADD_DIALOG = "itemName"
    }

    interface OnDialogResponse {
        fun onPositiveClicked(dialogId: Int?, bundle: Bundle?)
        fun onNegaiveClicked(dialogId: Int?, bundle: Bundle?)
    }

    fun attachFragment(onDialogResponseListener: OnDialogResponse) {
        this.onDialogResponseListener = onDialogResponseListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bundle = arguments
        val dialogId = bundle?.getInt(DIALOG_ID)

        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.add_edit_dialog, null)
        val editText: EditText = view!!.findViewById(R.id.add_edit_ed)

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(view)
            .setMessage("Add a new shopping list")
            .setPositiveButton("Add", DialogInterface.OnClickListener {dialog, which ->
                when(dialogId) {
                    ADD_LIST_DIALOG_ID -> {
                        val listName = editText.text.toString()
                        bundle.putString(LIST_NAME_ADD_DIALOG, listName)
                        onDialogResponseListener.onPositiveClicked(dialogId, bundle)
                    }
                    ADD_ITEM_DIALOG_ID -> {
                        val itemName = editText.text.toString()
                        bundle.putString(ITEM_NAME_ADD_DIALOG, itemName)
                        onDialogResponseListener.onPositiveClicked(dialogId, bundle)
                    }
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, which ->
                onDialogResponseListener.onNegaiveClicked(dialogId, bundle)
            })

        return alertDialogBuilder.create()
    }
}
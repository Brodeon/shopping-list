package com.brodeon.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class AddEditDialog : DialogFragment() {

    private lateinit var onDialogResponseListener: OnDialogResponse

    companion object {
        const val ADD_LIST_DIALOG_ID = 1
        const val ADD_ITEM_DIALOG_ID = 2
        const val EDIT_LIST_DIALOG_ID = 3
        const val EDIT_ITEM_DIALOG_ID = 4

        const val DIALOG_ID = "dialogId"
        const val ELEMENT_STRING = "elementString"
        const val DIALOG_MESSAGE = "message"
        const val EDITTEXT_HINT = "hint"
        const val ELEMENT_EDIT_ID = "elementId"
        const val POSITIVE_BTN_STRING = "positiveString"
    }

    interface OnDialogResponse {
        fun onPositiveClicked(dialogId: Int?, bundle: Bundle?)
        fun onNegativeClicked(dialogId: Int?, bundle: Bundle?)
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

        val positiveBtnString = bundle?.getString(POSITIVE_BTN_STRING)
        val message = bundle?.getString(DIALOG_MESSAGE)

        bundle?.getString(ELEMENT_STRING)?.let {
            editText.setText(it, TextView.BufferType.EDITABLE)
        }

        editText.hint = bundle?.getString(EDITTEXT_HINT)

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(view)
            .setMessage(message)
            .setPositiveButton(positiveBtnString, DialogInterface.OnClickListener {dialog, which ->
                val listName = editText.text.toString()
                bundle?.putString(ELEMENT_STRING, listName)
                onDialogResponseListener.onPositiveClicked(dialogId, bundle)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, which ->
                onDialogResponseListener.onNegativeClicked(dialogId, bundle)
            })
        val alertDialog = alertDialogBuilder.create()

        alertDialog.setOnShowListener(DialogInterface.OnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        })

        editText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //not implemented
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //not implemented
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                if (s != null) {
                    if (s.isNotEmpty()) {
                        positiveButton.isEnabled = true
                        return
                    }
                }

                positiveButton.isEnabled = false
            }
        })

        return alertDialog
    }


}
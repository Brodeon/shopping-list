package com.brodeon.shoppinglist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment

/**
 * Zajmuje się tworzeniem Dialogu, przyjęciem danych z Bundle oraz callbacami do klasy implementującej OnDialogResponse
 */
class AddEditDialog : DialogFragment() {

    private lateinit var onDialogResponseListener: OnDialogResponse

    /**
     * Zmienne stosowane jako wartość klucza w Bundle
     */
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

    /**
     * Interfejs, którego metody są wywoływane gdy naciśnie się positive button i negative button
     */
    interface OnDialogResponse {
        fun onPositiveClicked(dialogId: Int?, bundle: Bundle?)
        fun onNegativeClicked(dialogId: Int?, bundle: Bundle?)
    }

    /**
     * Powiązuje klasę implementującą OnDialogResponse. Jako że nie da się zrobić tego przez konstruktor,
     * musi być wywołana ta metoda
     */
    fun attachFragment(onDialogResponseListener: OnDialogResponse) {
        this.onDialogResponseListener = onDialogResponseListener
    }

    /**
     * Tworzy Dialog
     */
    @SuppressLint("InflateParams")
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
            .setPositiveButton(positiveBtnString) { _, _ ->
                val listName = editText.text.toString()
                bundle?.putString(ELEMENT_STRING, listName)
                onDialogResponseListener.onPositiveClicked(dialogId, bundle)
            }
            .setNegativeButton("Cancel") {_, _ ->
                onDialogResponseListener.onNegativeClicked(dialogId, bundle)
            }

        val alertDialog = alertDialogBuilder.create()

        /**
         * Gdy jest wywołana metoda show(), odpala się poniższy kod i wyłącza positiveButton
         */
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        editText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //Nic nie rob
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Nic nie rob
            }

            /**
             * Gdy długość tekstu w EditText jest równa 0, positive button ma być wyłączony, jeśli tekst ma długość większą od 0
             * wtedy positive button ma być wyłączony
             */
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
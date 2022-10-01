package com.trifonovkv.production.ui.calc

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.trifonovkv.production.R


class SaveDialogFragment(private val fragment: Fragment) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(fragment.context!!)
        builder.setView(R.layout.dialog_save)

        // Create the AlertDialog object and return it
        return builder.create()
    }

    fun show(savedInstanceState: Bundle?) {
        val dialog = onCreateDialog(savedInstanceState)
        dialog.show()
        dialog.findViewById<TextView>(R.id.tvSave).setOnClickListener {
            dialog.dismiss()
            (fragment as CalcFragment).doPositiveClick()
        }

        dialog.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            dialog.dismiss()
        }
    }
}
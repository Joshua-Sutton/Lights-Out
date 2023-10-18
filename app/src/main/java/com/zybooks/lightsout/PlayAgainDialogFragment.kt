package com.zybooks.lightsout

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class PlayAgainDialogFragment(val negBtnFun: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        class WarningDialogFragment : DialogFragment() {
            override fun onCreateDialog(savedInstanceState: Bundle?)
                    : Dialog {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("Cheat?")
                builder.setMessage("Did you cheat?")
                builder.setPositiveButton("Yes", null)
                builder.setNegativeButton("No") { dialog, id ->
                    negBtnFun()
                }
                return builder.create()
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }
}
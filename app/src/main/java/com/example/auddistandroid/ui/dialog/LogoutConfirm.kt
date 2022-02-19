package com.example.auddistandroid.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.auddistandroid.App.Companion.preferences
import com.example.auddistandroid.R
import com.example.auddistandroid.utils.Keys

class LogoutConfirm : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.log_out)
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                preferences.edit().apply {
                    remove(Keys.AUTH_TOKEN)
                    remove(Keys.USERNAME)
                    apply()
                }
                findNavController().navigate(R.id.action_settingsFragment_to_connectionFragment)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .create()
    }
}
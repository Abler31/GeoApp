package com.abler31.geoapp.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.abler31.geoapp.R
import org.osmdroid.util.GeoPoint

class AddMarkerDialogFragment(
    private val geoPoint: GeoPoint,
    private val onMarkerAdded: (String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_add_marker, null)
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_marker)
            .setView(view)
            .setPositiveButton(R.string.add) { _, _ ->
                val name = (view?.findViewById<EditText>(R.id.etMarkerName)?.text ?: "").toString()
                onMarkerAdded(name)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
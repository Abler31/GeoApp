package com.abler31.geoapp.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.abler31.geoapp.R
import com.abler31.geoapp.domain.models.Marker

class MarkerOptionsDialogFragment(
    private val marker: Marker,
    private val onDeleteMarker: () -> Unit,
    private val navigateToMarker: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(marker.name)
            .setItems(
                arrayOf(
                    getString(R.string.delete_marker),
                    getString(R.string.navigate_to_marker)
                )
            ) { _, which ->
                when (which) {
                    0 -> onDeleteMarker()
                    1 -> navigateToMarker()
                }
            }
            .create()
    }
}
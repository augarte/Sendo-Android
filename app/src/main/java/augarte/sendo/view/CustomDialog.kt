package augarte.sendo.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import augarte.sendo.R


class CustomDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_structure, null)

        var dialog = AlertDialog.Builder(activity)
        dialog.setView(view)
        dialog.setPositiveButton("Accept") { _, _ ->  Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show() }
        dialog.setNegativeButton("Cancel") { _, _ ->  Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show() }
        dialog.setTitle("Title")

        return dialog.create()
    }

    fun setFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment, "DailogFragment")
        transaction?.commit()
    }
}

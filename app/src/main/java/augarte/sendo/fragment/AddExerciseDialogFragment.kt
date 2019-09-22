package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import augarte.sendo.R

class AddExerciseDialogFragment(private val listener: ExerciseListFragment.OnDialogClickListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_exercise, null)

        return AlertDialog.Builder(activity)
                .setTitle("Add exercise")
                .setPositiveButton("ADD") { _, _ ->

                }
                .setNegativeButton("Cancel") { _, _ ->
                    dismiss()
                }
                .setView(view)
                .create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onDialogDismiss()
    }
}
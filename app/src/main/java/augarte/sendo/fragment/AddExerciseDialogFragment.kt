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
                .setTitle(getString(R.string.sendo_dialog_add_exercise_title))
                .setPositiveButton(getString(R.string.sendo_add)) { _, _ ->

                }
                .setNegativeButton(getString(R.string.sendo_cancel)) { _, _ ->
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
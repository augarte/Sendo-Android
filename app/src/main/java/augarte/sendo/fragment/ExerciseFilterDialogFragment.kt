package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.FilterAdapter
import augarte.sendo.dataModel.ExerciseType
import augarte.sendo.database.SelectTransactions
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.dialog_filter.view.*

class ExerciseFilterDialogFragment(private val listener: ExerciseFilterDialogListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_filter, null)

        val types = MainActivity.dbHandler.getExerciseType(SelectTransactions.SELECT_ALL_EXERCISETYPE, null)
        view.filterList.layoutManager = FlexboxLayoutManager(context)
        val filterAdapter = FilterAdapter(types)
        view.filterList.adapter = filterAdapter

        return AlertDialog.Builder(activity)
            .setPositiveButton(getString(R.string.sendo_accept)) { _, _ ->
                listener.onAccept(filterAdapter.getSelectedTypeList(), filterAdapter.getIsFavselected())
            }
            .setNegativeButton(getString(R.string.sendo_cancel)) { _, _ -> }
            .setView(view)
            .create()
    }

    interface ExerciseFilterDialogListener{
        fun onAccept(exerciseTypeList: ArrayList<ExerciseType>, favSelected: Boolean)
    }

}
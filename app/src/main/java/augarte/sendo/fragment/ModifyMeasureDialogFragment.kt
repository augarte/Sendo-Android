package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import augarte.sendo.R
import augarte.sendo.dataModel.Measurement
import augarte.sendo.utils.Utils
import augarte.sendo.utils.toStringFormat

class ModifyMeasureDialogFragment(private val measurement: Measurement, private val listener: MeasurementsFragment.MeasureEditListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_measure_modify, null)

        view.findViewById<TextView>(R.id.dateTV).text =  Utils.dateToString(measurement.date)
        view.findViewById<EditText>(R.id.modifyET).setText(measurement.value?.toStringFormat)

        return AlertDialog.Builder(activity)
                .setTitle(getString(R.string.sendo_modify))
                .setPositiveButton(getString(R.string.sendo_modify)) { _, _ ->
                    measurement.value = view.findViewById<EditText>(R.id.modifyET).text.toString().toDouble()
                    listener.onModify(measurement)
                }
                .setNegativeButton(getString(R.string.sendo_delete)) { _, _ ->
                    listener.onDelete(measurement)
                }
                .setView(view)
                .create()
    }
}
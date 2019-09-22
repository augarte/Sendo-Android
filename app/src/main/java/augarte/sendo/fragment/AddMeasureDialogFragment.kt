package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import augarte.sendo.activity.MainActivity
import augarte.sendo.dataModel.Measurement
import augarte.sendo.database.SelectTransactions
import augarte.sendo.utils.Utils
import kotlinx.android.synthetic.main.dialog_add_measure.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.text.TextUtils
import augarte.sendo.R
import augarte.sendo.dataModel.MeasureType

class AddMeasureDialogFragment(private val type: MeasureType, private val listener: MeasurementsFragment.OnDialogClickListener): DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    private val today = Calendar.getInstance()
    private val c = Calendar.getInstance()
    private var year = c.get(Calendar.YEAR)
    private var month = c.get(Calendar.MONTH)+1
    private var day = c.get(Calendar.DAY_OF_MONTH)

    private lateinit var dateValue: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_measure, null)

        val list = MainActivity.dbHandler!!.getMeasureType(SelectTransactions.SELECT_ALL_MEASURETYPE, null)
        val items = MutableList(list!!.size) { i -> list[i].name}

        val arrayAdapter = ArrayAdapter<String>(context!!, R.layout.item_spinner, items)
        view.measureTypeSpiner.adapter = arrayAdapter
        view.measureTypeSpiner.setSelection(list.indexOf(list.find{ x-> x.id == type.id }))

        dateValue = view.dateValue
        dateValue.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context!!, this, year, month-1, day)
            datePickerDialog.datePicker.maxDate = today.timeInMillis
            datePickerDialog.show()
        }
        setDate()

        return AlertDialog.Builder(activity)
                .setTitle("Add measure")
                .setPositiveButton("ADD") { _, _ ->
                    val value = view.valueET.text.toString().trim()
                    if (TextUtils.isEmpty(value)) {
                        view.valueET.error = "Enter a value"
                    } else {
                        val newMeasurement = Measurement()
                        newMeasurement.value = Integer.parseInt(view.valueET.text.toString())
                        newMeasurement.date = c.time
                        newMeasurement.type = list[view.measureTypeSpiner.selectedItemPosition]
                        MainActivity.dbHandler!!.insertMeasurement(newMeasurement)
                        listener.onDialogAccept(this)
                    }
                }
                .setNegativeButton("Cancel") { _, _ ->
                    dismiss()
                }
                .setView(view)
                .create()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        year = p1
        month = p2+1
        day = p3

        c.time = Utils.partseDatePickerValues(year, month, day)
        setDate()
    }

    private fun setDate(){
        when {
            today.get(Calendar.DATE) == c.get(Calendar.DATE) -> dateValue.text = getString(R.string.today)
            today.get(Calendar.DATE) - c.get(Calendar.DATE) == 1 -> dateValue.text = getString(R.string.yesterday)
            c.get(Calendar.DATE) - today.get(Calendar.DATE) == 1 -> dateValue.text = getString(R.string.tomorrow)
            else -> dateValue.text = dateFormat.format(c.time)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onDialogDismiss()
    }
}
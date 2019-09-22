package augarte.sendo.fragment

import android.app.AlertDialog
import android.icu.util.Measure
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.LineChartAdapter
import augarte.sendo.adapter.MeasurementAdapter
import augarte.sendo.dataModel.DateType
import augarte.sendo.dataModel.MeasureType
import augarte.sendo.dataModel.Measurement
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.fragment_measurements.*
import java.util.*
import kotlin.collections.ArrayList

class MeasurementsFragment : Fragment() {

    private val today = Calendar.getInstance()
    private var selectedMeasureType: Int = 0
    private var selectedDateType: Int = 0

    private var measurements: ArrayList<Measurement> = ArrayList()
    private var lineChartAdapter: LineChartAdapter = LineChartAdapter(measurements)

    private lateinit var measurementTypeList: ArrayList<MeasureType>
    private lateinit var dateTypeList: ArrayList<DateType>
    private lateinit var measurementAdapter: MeasurementAdapter
    private lateinit var lastMeasurement: Measurement

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        measurementTypeList = MainActivity.dbHandler!!.getMeasureType(SelectTransactions.SELECT_ALL_MEASURETYPE, null)!!
        val items1 = Array(measurementTypeList.size) { i -> measurementTypeList[i].name!!}
        measureType.text = items1[selectedMeasureType]
        measureType.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Meassurements")
                .setSingleChoiceItems(items1, selectedMeasureType) { dialog, item ->
                    selectedMeasureType = item
                    measureType.text = items1[selectedMeasureType]
                    refreshData()
                    Handler().postDelayed({dialog.dismiss()}, 500)
                }
                .show()
        }

        dateTypeList = MainActivity.dbHandler!!.getDateType(SelectTransactions.SELECT_ALL_DATETYPE, null)!!
        val items2 = Array(dateTypeList.size) { i -> dateTypeList[i].name!!}
        dateRange.text = items2[selectedDateType]
        dateRange.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Period")
                .setSingleChoiceItems(items2, selectedDateType) { dialog, item ->
                    selectedDateType = item
                    dateRange.text = items2[selectedDateType]
                    refreshData()
                    Handler().postDelayed({dialog.dismiss()}, 500)
                }
                .show()
        }

        val lastMeasurementArray = MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_LAST_DATE, arrayOf(measurementTypeList[selectedMeasureType].id.toString()))
        if (lastMeasurementArray.size>0) lastMeasurement = lastMeasurementArray.first()
        measurements.addAll(getMeasurementsByDateType(measurementTypeList[selectedMeasureType], dateTypeList[selectedDateType]))
        measurementAdapter = MeasurementAdapter(measurements)
        measureValuesRV.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            adapter = measurementAdapter
        }
        sparkview.setScrubListener {value ->
            if (value==null) scrub.visibility = View.GONE
            else {
                scrub.text = "$value"
                scrub.visibility = View.VISIBLE
            }
        }
        sparkview.adapter = lineChartAdapter
        lineChartAdapter.notifyDataSetChanged()

        val listener = object: OnDialogClickListener {
            override fun onDialogAccept(dialog: DialogFragment) {
                refreshData()
                dialog.dismiss()
            }
            override fun onDialogDismiss() {
                fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
            }
        }

        fab_add.setOnClickListener {
            val addMeasureDialog = AddMeasureDialogFragment(measurementTypeList[selectedMeasureType], listener)
            addMeasureDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
            if(!addMeasureDialog.isAdded){
                addMeasureDialog.show(fragmentManager!!, addMeasureDialog.tag)
                fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
            }
        }

        checkNoMeasurements()
    }

    private fun refreshData(){
        measurements.clear()
        measurements.addAll(getMeasurementsByDateType(measurementTypeList[selectedMeasureType], dateTypeList[selectedDateType]))
        measurementAdapter.notifyDataSetChanged()
        lineChartAdapter.notifyDataSetChanged()
        checkNoMeasurements()
    }

    private fun getMeasurementsByDateType(measureType: MeasureType, dateType: DateType): ArrayList<Measurement> {
        val aux = today.clone() as Calendar
        when {
            dateType.code == "ALL" -> {
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_ORDER_DATE, arrayOf(measureType.id.toString()))
            }
            dateType.code == "1WE" -> {
                aux.add(Calendar.DATE,-7)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            dateType.code == "3WE" -> {
                aux.add(Calendar.DATE,-21)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            dateType.code == "1MO" -> {
                aux.add(Calendar.DATE,-30)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            dateType.code == "2MO" -> {
                aux.add(Calendar.DATE,-60)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            dateType.code == "3MO" -> {
                aux.add(Calendar.DATE,-90)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            dateType.code == "6MO" -> {
                aux.add(Calendar.DATE,-180)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            dateType.code == "1YR" -> {
                aux.add(Calendar.DATE,-365)
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
            else -> {
                return MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE, arrayOf(measureType.id.toString(), "${aux.time.time/1000}", "${today.time.time/1000}"))
            }
        }
    }

    private fun checkNoMeasurements() {
        when {
            measurements.size == 0 -> {
                measureValuesRV.visibility = View.GONE
                no_data.visibility = View.VISIBLE
                no_measure.visibility = View.VISIBLE
            }
            measurements.size == 1 -> {
                no_data.visibility = View.VISIBLE
                no_measure.visibility = View.GONE
                measureValuesRV.visibility = View.VISIBLE
            }
            else -> {
                no_data.visibility = View.GONE
                no_measure.visibility = View.GONE
                measureValuesRV.visibility = View.VISIBLE
            }
        }
    }

    interface OnDialogClickListener {
        fun onDialogAccept(dialog: DialogFragment)
        fun onDialogDismiss()
    }
}
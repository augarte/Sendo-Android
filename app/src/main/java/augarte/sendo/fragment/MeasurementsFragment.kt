package augarte.sendo.fragment

import android.app.AlertDialog
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
import kotlin.collections.ArrayList

class MeasurementsFragment : Fragment() {

    private var selectedMeasureType: Int = 0
    private var selectedDateType: Int = 0

    private var measurements: ArrayList<Measurement> = ArrayList()
    private var lineChartAdapter: LineChartAdapter = LineChartAdapter(measurements)

    private lateinit var measurementTypeList: ArrayList<MeasureType>
    private lateinit var dateTypeList: ArrayList<DateType>
    private lateinit var measurementAdapter: MeasurementAdapter

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
                    Handler().postDelayed({dialog.dismiss()}, 500)
                }
                .show()
        }

        measurements.addAll(MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_ORDER_DATE, arrayOf(measurementTypeList[selectedMeasureType].id.toString())))
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

        val addMeasureDialog = AddMeasureDialogFragment(listener)
        addMeasureDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
        fab_add.setOnClickListener {
            if(!addMeasureDialog.isAdded){
                addMeasureDialog.show(fragmentManager!!, addMeasureDialog.tag)
                fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
            }
        }

        checkNoMeasurements()
    }

    private fun refreshData(){
        measurements.clear()
        measurements.addAll(MainActivity.dbHandler!!.getMeasurement(SelectTransactions.SELECT_MEASUREMENT_BY_TYPE_ORDER_DATE, arrayOf(measurementTypeList[selectedMeasureType].id.toString())))
        measurementAdapter.notifyDataSetChanged()
        lineChartAdapter.notifyDataSetChanged()
        checkNoMeasurements()
    }

    private fun checkNoMeasurements() {
        if (measurements.size == 0) {
            measureValuesRV.visibility = View.GONE
            no_data.visibility = View.VISIBLE
            no_measure.visibility = View.VISIBLE
        } else if (measurements.size == 1) {
            no_data.visibility = View.VISIBLE
            no_measure.visibility = View.GONE
            measureValuesRV.visibility = View.VISIBLE
        } else {
            no_data.visibility = View.GONE
            no_measure.visibility = View.GONE
            measureValuesRV.visibility = View.VISIBLE
        }
    }

    interface OnDialogClickListener {
        fun onDialogAccept(dialog: DialogFragment)
        fun onDialogDismiss()
    }
}
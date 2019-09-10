package augarte.sendo.fragment

import android.app.AlertDialog
import android.graphics.PointF
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.MeasurementAdapter
import augarte.sendo.dataModel.DateType
import augarte.sendo.dataModel.MeasureType
import augarte.sendo.dataModel.Measurement
import augarte.sendo.database.SelectTransactions
import augarte.sendo.view.CustomDialog
import augarte.sendo.view.LineChart
import kotlinx.android.synthetic.main.fragment_measurements.*
import kotlin.collections.ArrayList


class MeasurementsFragment : Fragment() {

    private var selecterMeasureType: Int = 0
    private var selecterDateType: Int = 0

    private  var selecterAux: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart : LineChart = view.findViewById(R.id.chart)
        if (true) {
            chart.setData(arrayOf(PointF(15f, 39f), PointF(20f, 21f), PointF(28f, 9f), PointF(37f, 21f), PointF(40f, 25f), PointF(50f, 31f), PointF(62f, 24f), PointF(80f, 28f)))
        }else {
            chart.visibility = View.GONE
            view.findViewById<TextView>(R.id.chart_no_data)?.visibility = View.VISIBLE
        }

        val list1: ArrayList<MeasureType> = MainActivity.dbHandler!!.getMeasureType(SelectTransactions.SELECT_ALL_MEASURETYPE, null)!!
        val items1 = Array(list1.size) { i -> list1[i].name!!}
        measureType.text = items1[0]
        measureType.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Meassurements")
                .setSingleChoiceItems(items1, selecterMeasureType) { dialog, item ->
                    selecterMeasureType = item
                    measureType.text = items1[selecterMeasureType]
                    Handler().postDelayed({dialog.dismiss()}, 500)
                }
                .show()
        }

        val list2: ArrayList<DateType> = MainActivity.dbHandler!!.getDateType(SelectTransactions.SELECT_ALL_DATETYPE, null)!!
        val items2 = Array(list2.size) { i -> list2[i].name!!}
        dateRange.text = items2[0]
        dateRange.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Period")
                .setSingleChoiceItems(items2, selecterDateType) { dialog, item ->
                    selecterDateType = item
                    dateRange.text = items2[selecterDateType]
                    Handler().postDelayed({dialog.dismiss()}, 500)
                }
                .show()
        }


        val measurements : ArrayList<Measurement> = ArrayList()
        val m = Measurement()
        m.value = 70
        measurements.add(m)

        measureValuesRV.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = MeasurementAdapter(measurements)
        }

        fab_add.setOnClickListener {
            val dialog = CustomDialog()
            dialog.show(fragmentManager!!, "MeassurementDialog")
            fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
        }
    }
}
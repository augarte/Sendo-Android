package augarte.sendo.fragment

import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.R
import augarte.sendo.adapter.MeasurementAdapter
import augarte.sendo.dataModel.Measurement
import augarte.sendo.view.CustomDialog
import augarte.sendo.view.LineChart
import kotlinx.android.synthetic.main.fragment_measurements.*

class MeasurementsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var chart : LineChart = view.findViewById(R.id.chart)
        if (true) {
            chart.setData(arrayOf(PointF(15f, 39f), PointF(20f, 21f), PointF(28f, 9f), PointF(37f, 21f), PointF(40f, 25f), PointF(50f, 31f), PointF(62f, 24f), PointF(80f, 28f)))
        }else {
            chart.visibility = View.GONE
            view.findViewById<TextView>(R.id.chart_no_data)?.visibility = View.VISIBLE
        }


        var measurements : ArrayList<Measurement> = ArrayList()
        var m = Measurement()
        m.value = 70
        measurements.add(m)

        measureValuesRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            adapter = MeasurementAdapter(measurements)
        }

        fab_add.setOnClickListener {
            var dialog = CustomDialog()
            dialog.show(fragmentManager!!, "MeassurementDialog")
            fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
        }
    }
}
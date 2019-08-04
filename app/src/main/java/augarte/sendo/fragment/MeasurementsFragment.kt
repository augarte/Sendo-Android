package augarte.sendo.fragment

import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import augarte.sendo.R
import augarte.sendo.view.LineChart

class MeasurementsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_measurements, container, false)

        var chart : LineChart = view.findViewById(R.id.chart)
        if (true) {
            chart.setData(arrayOf(PointF(15f, 39f), PointF(20f, 21f), PointF(28f, 9f), PointF(37f, 21f), PointF(40f, 25f), PointF(50f, 31f), PointF(62f, 24f), PointF(80f, 28f)))
        }else {
            chart.visibility = View.GONE
            view.findViewById<TextView>(R.id.chart_no_data)?.visibility = View.VISIBLE
        }

        return view
    }
}
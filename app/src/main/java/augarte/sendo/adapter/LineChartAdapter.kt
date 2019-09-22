package augarte.sendo.adapter

import augarte.sendo.dataModel.Measurement
import com.robinhood.spark.SparkAdapter

class LineChartAdapter(private val measurements: ArrayList<Measurement>): SparkAdapter() {

    override fun getX(index: Int): Float {
        return (measurements[index].date!!.time/86400000).toFloat()
    }

    override fun getY(index: Int): Float {
        return measurements[index].value!!.toFloat()
    }

    override fun getItem(index: Int): Any {
        return measurements[index].value!!
    }

    override fun getCount(): Int {
        return measurements.size
    }
}
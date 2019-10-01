package augarte.sendo.adapter

import augarte.sendo.dataModel.Serie
import com.robinhood.spark.SparkAdapter

class ProgressChartAdapter(private val progress: ArrayList<Serie>): SparkAdapter() {

    override fun getX(index: Int): Float {
        return (progress[index].week).toFloat()
    }

    override fun getY(index: Int): Float {
        return progress[index].weight!!.toFloat()
    }

    override fun getItem(index: Int): Any {
        return progress[index].weight!!
    }

    override fun getCount(): Int {
        return progress.size
    }
}
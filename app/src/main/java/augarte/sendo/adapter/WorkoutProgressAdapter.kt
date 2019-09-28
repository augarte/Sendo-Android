package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Measurement
import augarte.sendo.dataModel.Serie
import augarte.sendo.dataModel.Workout
import com.robinhood.spark.SparkView
import kotlinx.android.synthetic.main.item_measurement.*
import kotlinx.android.synthetic.main.item_progress_card.view.*
import java.util.*
import kotlin.collections.ArrayList

class WorkoutProgressAdapter(private val items: ArrayList<Serie>): RecyclerView.Adapter<WorkoutProgressAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Workout, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_progress_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val list: ArrayList<Measurement> = ArrayList()

        val m = Measurement()
        m.value = 100.0
        m.date = Date()
        list.add(m)
        val m2 = Measurement()
        m2.value = 200.0
        m2.date = Date((Date().time+100000000))
        list.add(m2)

        holder.max.text =list.maxBy { x -> x.value!! }?.value.toString()
        holder.min.text =list.minBy { x -> x.value!! }?.value.toString()

        holder.sparkView.adapter = LineChartAdapter(list)

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sparkView: SparkView = view.sparkView
        val title: TextView = view.title
        val max: TextView = view.max
        val min: TextView = view.min
    }
}
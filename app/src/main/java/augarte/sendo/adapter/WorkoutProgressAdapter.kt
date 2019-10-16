package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.ExerciseDay
import augarte.sendo.dataModel.Workout
import com.robinhood.spark.SparkView
import kotlinx.android.synthetic.main.item_progress_card.view.*

class WorkoutProgressAdapter(private val items: ArrayList<ExerciseDay>): RecyclerView.Adapter<WorkoutProgressAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Workout, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_progress_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.exercise!!.name

        val max = item.series.maxBy { x -> x.weight!! }?.weight
        val min = item.series.minBy { x -> x.weight!! }?.weight

        holder.max.text = max?.toString() ?: ""
        holder.min.text = min?.toString() ?: ""

        holder.sparkView.adapter = ProgressChartAdapter(item.series)

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
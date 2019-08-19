package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.Measurement
import kotlinx.android.synthetic.main.item_measurement.view.*

class CreateWorkoutExerciseAdapter(private val items : ArrayList<Exercise>) : RecyclerView.Adapter<CreateWorkoutExerciseAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Measurement, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.card_title?.text = item.name.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val card_title : TextView? = view.value

        init {

        }
    }
}
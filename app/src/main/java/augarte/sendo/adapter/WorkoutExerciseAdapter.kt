package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.item_workout_exercise.view.*

class WorkoutExerciseAdapter(private val items: ArrayList<Exercise>): RecyclerView.Adapter<WorkoutExerciseAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Workout, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_workout_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.exerciseName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.exerciseName
        val serieRep: TextView = view.serieRep
    }
}
package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Day
import augarte.sendo.dataModel.ExerciseDay
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.item_workout_day.view.*

class WorkoutDayAdapter(private val items: ArrayList<Day>): RecyclerView.Adapter<WorkoutDayAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Workout, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_workout_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.name
        holder.exerciseList.text = getExerciseListText(item.exerciseDay)
    }

    private fun getExerciseListText(exerciseDays: ArrayList<ExerciseDay>): String {
        var result = ""
        for (exerciseDay in exerciseDays) {
            result += "${exerciseDay.serieNum}x${exerciseDay.repNum} ${exerciseDay.exercise!!.name}\n"
        }
        return result
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.dayTitle
        val exerciseList: TextView = view.exercise_list
    }
}

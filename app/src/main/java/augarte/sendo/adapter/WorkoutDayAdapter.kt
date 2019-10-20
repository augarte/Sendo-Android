package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Day
import augarte.sendo.dataModel.ExerciseDay
import augarte.sendo.utils.toStringFormat
import kotlinx.android.synthetic.main.item_workout_day.view.*

class WorkoutDayAdapter(private val items: ArrayList<Day>): RecyclerView.Adapter<WorkoutDayAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Day, View>) -> Unit)? = null
    var week : Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_workout_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.name
        holder.exerciseList.text = getExerciseListText(item.exerciseDayList)
        holder.itemView.setOnClickListener { onItemClick?.invoke(Pair<Day, View>(item, holder.title)) }
    }

    private fun getExerciseListText(exerciseDays: ArrayList<ExerciseDay>): String {
        var result = ""
        for (exerciseDay in exerciseDays) {
            var serieWork = ""
            for (serie in exerciseDay.series){
                if (serie.week == week){
                    serieWork += if (exerciseDay.series.indexOf(serie)==0) "${serie.weight?.toStringFormat}"
                    else " + ${serie.weight?.toStringFormat}"
                }
            }
            if(serieWork.isNotEmpty()) serieWork = "($serieWork)"
            result += "${exerciseDay.serieNum}x${exerciseDay.repNum} ${exerciseDay.exercise!!.name} ${serieWork}\n"
        }
        return result
    }

    fun setWeekNumber(weekNumber: Int) {
        week = weekNumber
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.dayTitle
        val exerciseList: TextView = view.exercise_list
    }
}

package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.WorkoutDayActivity
import augarte.sendo.dataModel.ExerciseDay
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_exercise_day.view.*

class ExerciseDayAdapter(private val items: ArrayList<ExerciseDay>, private val listener: WorkoutDayActivity.OnExerciseDayClickListener): RecyclerView.Adapter<ExerciseDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_exercise_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.exercise!!.name
        holder.itemView.setOnClickListener { listener.onItemClick(item) }

        Glide.with(holder.image.context).load(item.exercise!!.imageURL).into(holder.image)
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

        val title: TextView = view.exerciseTitle
        val image: ImageView = view.image1

    }
}
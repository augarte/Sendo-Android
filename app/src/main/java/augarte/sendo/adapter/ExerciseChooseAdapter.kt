package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Exercise
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseChooseAdapter(private val items : ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseChooseAdapter.MainViewHolder>() {

    var onItemClick: ((Pair<Exercise, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_exercise_chooser, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]

        holder.exerciseName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exerciseName: TextView = view.exercise_name
    }
}
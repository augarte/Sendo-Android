package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Exercise
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseListAdapter(private val items : MutableList<Exercise>) : RecyclerView.Adapter<ExerciseListAdapter.MainViewHolder>() {

    var onItemClick: ((Pair<Exercise, View>) -> Unit)? = null

    private var removedPosition: Int = -1
    private lateinit var removedItem: Exercise

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_exercise, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]

        holder.exerciseName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeWithSwipe(viewHolder: RecyclerView.ViewHolder) {
        removedPosition = viewHolder.adapterPosition
        removedItem = items[viewHolder.adapterPosition]
        items.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "$removedItem.name deleted.", Snackbar.LENGTH_LONG).setAction("UNDO") {
            items.add(removedPosition, removedItem)
            notifyItemInserted(removedPosition)
        }.show()
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exerciseName: TextView = view.exercise_name
    }
}
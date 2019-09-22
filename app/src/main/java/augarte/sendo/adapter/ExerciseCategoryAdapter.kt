package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.dataModel.Exercise
import augarte.sendo.database.DatabaseConstants
import com.google.android.material.snackbar.Snackbar
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import kotlinx.android.synthetic.main.item_exercise.view.*
import kotlinx.android.synthetic.main.item_exercise.view.exercise_name
import kotlinx.android.synthetic.main.item_exercise_category.view.*

class ExerciseCategoryAdapter(private val items : MutableList<Exercise>) : RecyclerView.Adapter<ExerciseCategoryAdapter.MainViewHolder>(), FastScrollRecyclerView.SectionedAdapter {

    var onItemClick: ((Pair<Exercise, View>) -> Unit)? = null

    private var removedPosition: Int = -1
    private lateinit var removedItem: Exercise
    private var lastCategory = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_exercise_category, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]

        if (lastCategory != item.type!!.id){
            holder.categoryTitle.text = item.type!!.name
            holder.categoryLayout.visibility = View.VISIBLE
        }
        else{
            holder.categoryLayout.visibility = View.GONE
        }
        lastCategory = item.type!!.id!!

        holder.exerciseName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getSectionName(position: Int): String {
        val item = items[position]
        return if (item.type!!.name!!.trim().isNotEmpty()) item.type!!.name!!.substring(0,3)
        else ""
    }

    fun removeWithSwipe(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        removedPosition = position
        removedItem = items[position]
        removedItem.state = DatabaseConstants.STATE_ARCHIVED
        MainActivity.dbHandler!!.updateExerciseState(removedItem)
        items.removeAt(position)
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "${removedItem.name} archived.", Snackbar.LENGTH_LONG).setAction("UNDO") {
            removedItem.state = DatabaseConstants.STATE_ACTIVE
            MainActivity.dbHandler!!.updateExerciseState(removedItem)
            items.add(removedPosition, removedItem)
            notifyItemInserted(removedPosition)
        }.show()
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exerciseName: TextView = view.exercise_name
        var categoryLayout: LinearLayout = view.category_layout
        var categoryTitle: TextView = view.category_title
    }
}
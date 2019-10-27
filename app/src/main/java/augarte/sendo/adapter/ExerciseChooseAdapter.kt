package augarte.sendo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.ExerciseDay
import augarte.sendo.fragment.AddExerciseToWorkoutDialogFragment
import augarte.sendo.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_exercise.view.exercise_name
import kotlinx.android.synthetic.main.item_exercise_chooser.view.*

class ExerciseChooseAdapter(private var items: ArrayList<Exercise>, private val listener: CreateWorkoutAdapter.OnExerciseSelectedListener, private val context: Context) : RecyclerView.Adapter<ExerciseChooseAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_exercise_chooser, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]

        holder.exerciseName.text = item.name

        if (item.selected) {
            holder.selected.visibility = View.VISIBLE
            holder.itemView.background = ContextCompat.getDrawable(context, R.color.colorPrimary)
        }

        Glide.with(holder.exerciseImage.context).load(item.imageURL).placeholder(R.drawable.ic_sendo_placeholder).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.exerciseImage)

        holder.itemView.setOnClickListener {
            if (item.selected) {
                item.selected = false
                holder.selected.visibility = View.GONE
                holder.itemView.setBackgroundColor(Utils.getColorFromAttr(context, R.attr.colorPrimaryLight))
                listener.onDeselected(item)
            } else {
                val listener = object: ExerciseAddDialogListener {
                    override fun onExerciseAdded(exerciseDay: ExerciseDay) {
                        item.selected = true
                        holder.selected.visibility = View.VISIBLE
                        holder.itemView.setBackgroundColor(Utils.getColorFromAttr(context, R.attr.colorPrimary))
                        listener.onSelect(exerciseDay)
                    }
                }
                val manager = (context as FragmentActivity).supportFragmentManager
                val addExerciseToWorkoutDialogFragment = AddExerciseToWorkoutDialogFragment(item, listener)
                addExerciseToWorkoutDialogFragment.show(manager, "DIALOG")
            }
        }
    }

    fun setFilter(newList: MutableList<Exercise>) {
        items = ArrayList()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exerciseName: TextView = view.exercise_name
        var selected: ImageView = view.selected_image
        var exerciseImage: ImageView = view.exercise_image
    }

    interface ExerciseAddDialogListener{
        fun onExerciseAdded(exerciseDay: ExerciseDay)
    }
}
package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.*
import augarte.sendo.dataModel.Day
import augarte.sendo.dataModel.Exercise
import kotlinx.android.synthetic.main.item_day_card.view.*

class CreateWorkoutAdapter(private val items : ArrayList<Day>) : RecyclerView.Adapter<CreateWorkoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.card_title?.text = item.name.toString()
        holder.exercise_list.text = createExerciseList(item.getExercises())

        var listener = View.OnClickListener {
            if (holder.arrow.rotation == 0f) {
                Animations.rotate(holder.arrow, 180f)
                Animations.toggleCardBody(holder.card as View, 75.toPx, 175.toPx)
            } else {
                Animations.rotate(holder.arrow, 0f)
                Animations.toggleCardBody(holder.card as View, 175.toPx, 75.toPx)
            }
        }

        holder.card.setOnClickListener{
            holder.arrow.performClick()
            holder.arrow.isPressed = true
            holder.arrow.invalidate()
            holder.arrow.isPressed = false
            holder.arrow.invalidate()
        }
        holder.arrow.setOnClickListener(listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun createExerciseList(exercises : ArrayList<Exercise>) : String {
        var exerciseListString = ""
        for (e in exercises) {
            exerciseListString += "\n" + e.name
        }
        return exerciseListString
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val card : CardView = view.card
        val card_title : TextView = view.card_title
        val exercise_list : TextView = view.exercise_list
        val arrow : ImageButton = view.drop_arrow

    }
}
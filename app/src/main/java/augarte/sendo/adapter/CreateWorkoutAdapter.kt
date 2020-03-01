package augarte.sendo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Day
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.ExerciseDay
import augarte.sendo.utils.Animations
import augarte.sendo.utils.toPx
import kotlinx.android.synthetic.main.item_day_card.view.*

class CreateWorkoutAdapter(private val items : ArrayList<Day>, private val rv : RecyclerView) : RecyclerView.Adapter<CreateWorkoutAdapter.ViewHolder>() {

    private var selectedExerciseList = ArrayList<ArrayList<Exercise>>()
    private lateinit var dayEditListener: WorkoutdayEditListener
    private lateinit var inflater: LayoutInflater
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        inflater = LayoutInflater.from(context)
        selectedExerciseList.clear()
        for (i in 0 until items.size) {
            selectedExerciseList.add(ArrayList())
        }
        val view = inflater.inflate(R.layout.item_day_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        for (exerciseDay in item.exerciseDayList) {
            selectedExerciseList[position].add(exerciseDay.exercise!!)
        }

        holder.cardTitle.text = item.name.toString()
        holder.exerciseList.text = createExerciseList(selectedExerciseList[position])

        holder.card.setOnClickListener{
            holder.arrow.performClick()
            holder.arrow.isPressed = true
            holder.arrow.invalidate()
            holder.arrow.isPressed = false
            holder.arrow.invalidate()
        }

        holder.arrow.setOnClickListener {
            if (holder.arrow.rotation == 0f) {
                Animations.rotate(holder.arrow, 180f)
                Animations.toggleCardBody(holder.card, 80.toPx, 180.toPx)
            } else {
                Animations.rotate(holder.arrow, 0f)
                Animations.toggleCardBody(holder.card, 180.toPx, 80.toPx)
            }
        }

        holder.edit.setOnClickListener{
            dayEditListener.onEditPressed(item.name.toString(), selectedExerciseList[position], object: OnExerciseSelectedListener {
                override fun onTitleChanged(title: String) {
                    holder.cardTitle.text = title
                    item.name = title
                }

                override fun onSelect(exerciseDay: ExerciseDay) {
                    selectedExerciseList[position].add(exerciseDay.exercise!!)
                    exerciseDay.dayId = item.id
                    item.exerciseDayList.add(exerciseDay)

                    holder.exerciseList.text = createExerciseList(selectedExerciseList[position])
                }

                override fun onDeselected(exercise: Exercise) {
                    val e = selectedExerciseList[position].find { x -> x.id== exercise.id }
                    if (e!=null) {
                        val index = selectedExerciseList[position].indexOf(e)
                        selectedExerciseList[position].removeAt(index)

                        item.exerciseDayList.removeAt(index)

                        holder.exerciseList.text = createExerciseList(selectedExerciseList[position])
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(){
        selectedExerciseList.add(ArrayList())
        notifyItemInserted(selectedExerciseList.size-1)
    }

    fun deleteItem(pos : Int){
        selectedExerciseList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun setDayEditListener(l: WorkoutdayEditListener) {
        dayEditListener = l
    }

    fun createExerciseList(exercises: ArrayList<Exercise>) : String {
        var exerciseListString = ""
        for (exercise in exercises) {
            exerciseListString += "${exercise.name}\n"
        }
        return exerciseListString
    }

    interface WorkoutdayEditListener {
        fun onEditPressed(title: String, selectedExercises: ArrayList<Exercise>, listener: OnExerciseSelectedListener)
    }

    interface OnExerciseSelectedListener {
        fun onSelect(exerciseDay: ExerciseDay)
        fun onDeselected(exercise: Exercise)
        fun onTitleChanged(title: String)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card : CardView = view.card
        val cardTitle : TextView = view.card_title
        val exerciseList : TextView = view.exercise_list
        val arrow : ImageButton = view.drop_arrow
        val edit : ImageButton = view.edit
    }
}
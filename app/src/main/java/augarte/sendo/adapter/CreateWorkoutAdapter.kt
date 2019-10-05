package augarte.sendo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.*
import augarte.sendo.dataModel.Day
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.ExerciseDay
import augarte.sendo.utils.Animations
import augarte.sendo.utils.toPx
import augarte.sendo.utils.toPxF
import kotlinx.android.synthetic.main.item_day_card.view.*

class CreateWorkoutAdapter(private val items : ArrayList<Day>, private val rv : RecyclerView) : RecyclerView.Adapter<CreateWorkoutAdapter.ViewHolder>() {

    var onDayEdit: ((title: String, selectedExercises: ArrayList<Exercise>, listener: OnExerciseSelectedListener) -> Unit)? = null

    private var selectedExerciseList = ArrayList<Exercise>()
    private lateinit var inflater: LayoutInflater
    private lateinit var context: Context
    var newItem : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_day_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        for (exerciseDay in item.exerciseDayList) {
            selectedExerciseList.add(exerciseDay.exercise!!)
        }

        holder.cardTitle.text = item.name.toString()
        holder.exerciseList.text = createExerciseList(selectedExerciseList)

        val listener = View.OnClickListener {
            if (holder.arrow.rotation == 0f) {
                Animations.rotate(holder.arrow, 180f)
                Animations.toggleCardBody(holder.card, 80.toPx, 180.toPx)
            } else {
                Animations.rotate(holder.arrow, 0f)
                Animations.toggleCardBody(holder.card, 180.toPx, 80.toPx)
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


        val exerciseSelectedListener = object: OnExerciseSelectedListener {
            override fun onSelect(exercise: Exercise) {
                selectedExerciseList.add(exercise)

                val exerciseDay = ExerciseDay()
                exerciseDay.dayId = item.id
                exerciseDay.exercise = exercise
                item.exerciseDayList.add(exerciseDay)

                holder.exerciseList.text = createExerciseList(selectedExerciseList)
            }

            override fun onDeselected(exercise: Exercise) {
                val e = selectedExerciseList.find { x -> x.id== exercise.id }
                if (e!=null) {
                    val index = selectedExerciseList.indexOf(e)
                    selectedExerciseList.removeAt(index)

                    item.exerciseDayList.removeAt(index)

                    holder.exerciseList.text = createExerciseList(selectedExerciseList)
                }
            }
        }
        holder.edit.setOnClickListener{
            onDayEdit?.invoke(item.name.toString(), selectedExerciseList, exerciseSelectedListener)
        }

        if (position!=0 && !newItem)  {
            holder.itemView.z -= 1*position
            holder.itemView.y = ((-100)*position).toPxF
            holder.itemView.animate().setDuration(300).translationYBy((100*position).toPxF).zBy(1f*position)
            newItem = false
        }
        /*else if (newItem) {
            holder.itemView.z -= 1
            holder.itemView.y = ((-100)).toPxF
            holder.itemView.animate().setDuration(400).translationYBy((100).toPxF).zBy(1f)
        }*/
    }

    @Synchronized fun deleteItem(pos : Int){
        rv.post{
            val view = rv.layoutManager!!.getChildAt(pos)
            if(view != null){
                if (view.drop_arrow.rotation != 0f) {
                    view.drop_arrow?.animate()?.rotation(0f)
                    Animations.toggleCardBody(view.card as View, 180.toPx, 80.toPx)
                }
                view.z -= 1
                view.animate()?.translationYBy((-100).toPxF)
                        /*?.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        //view.animate()?.setDuration(0)?.alpha(0f)?.translationYBy((100).toPxF)
                        //rv.post { notifyItemRemoved(pos)  }
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                    }
                })*/
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun createExerciseList(exercises: ArrayList<Exercise>) : String {
        var exerciseListString = ""
        for (exercise in exercises) {
            exerciseListString += "${exercise.name}\n"
        }
        return exerciseListString
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card : CardView = view.card
        val cardTitle : TextView = view.card_title
        val exerciseList : TextView = view.exercise_list
        val arrow : ImageButton = view.drop_arrow
        val edit : ImageButton = view.edit
    }

    interface OnExerciseSelectedListener {
        fun onSelect(exercise: Exercise)
        fun onDeselected(exercise: Exercise)
    }
}
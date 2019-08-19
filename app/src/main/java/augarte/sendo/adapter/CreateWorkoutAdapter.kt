package augarte.sendo.adapter

import android.animation.ValueAnimator
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
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.R.attr.button
import android.util.Log
import com.leinardi.android.speeddial.SpeedDialView


class CreateWorkoutAdapter(private val items : ArrayList<Day>) : RecyclerView.Adapter<CreateWorkoutAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.card_title?.text = item.name.toString()


        holder.exercise_list.text = createExerciseList(item.getExercises())
       /* holder.day_exercise_rv.apply {
            layoutManager = LinearLayoutManager(holder.day_exercise_rv.context, LinearLayout.HORIZONTAL, false)
            adapter = CreateWorkoutExerciseAdapter(item.getExercises())
            setRecycledViewPool(viewPool)
        }*/

        var listener = View.OnClickListener {
            if (holder.arrow.rotation == 0f) {
                holder.arrow.animate().rotation(180f).start()
                toggleCardBody(holder.card as View, 75.toPx, 175.toPx)
            } else {
                holder.arrow.animate().rotation(0f).start()
                toggleCardBody(holder.card as View, 175.toPx, 75.toPx)
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


/*
    private fun deleteItem(context : Context, rowView: View, position: Int) {
        val anim = loadAnimation(context, android.R.anim.slide_out_right)
        anim.setDuration(300)
        findViewHolderForAdapterPosition(position).startAnimation(anim)

        Handler().postDelayed(Runnable {
            items.removeAt(position) //Remove the current content from the array
            notifyDataSetChanged() //Refresh list
        }, anim.getDuration())
    }
*/

    private fun toggleCardBody(body: View, from : Int, to : Int) {
        body.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val maxHeight = body.measuredHeight + body.paddingTop + body.paddingBottom
        //val startHeight = if (isToggled) maxHeight else 0
        //val targetHeight = if (isToggled) 0 else maxHeight

        val expandAnimator = ValueAnimator
                .ofInt(from, to)
                .setDuration(200)

        expandAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            body.layoutParams.height = value
            body.requestLayout()
        }

        expandAnimator.start()
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
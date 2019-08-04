package augarte.sendo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import augarte.sendo.R
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.item_workout_card.view.*

class WorkoutAdapter(val items : ArrayList<Workout>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_workout_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.titleTV?.text = items.get(position).name
        holder?.workoutCV?.background = items.get(position).image
    }

    override fun getItemCount(): Int {
        return items.size
    }



}


class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val workoutCV = view.workout_card
    val titleTV = view.card_text
}


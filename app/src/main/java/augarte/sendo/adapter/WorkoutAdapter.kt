package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import augarte.sendo.R
import augarte.sendo.dataModel.Workout

class WorkoutAdapter(val items : ArrayList<Workout>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_workout_card, parent, false)) {

    private var titleTV: TextView? = null

    init {
        titleTV = itemView.findViewById(R.id.card_text)
    }

    fun bind(workout: Workout) {
        titleTV?.text = workout.name
    }
}


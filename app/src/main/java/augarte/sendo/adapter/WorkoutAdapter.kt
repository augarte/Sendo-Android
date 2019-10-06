package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import augarte.sendo.R
import augarte.sendo.dataModel.Workout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_workout_card.view.*

class WorkoutAdapter(private val items : ArrayList<Workout>) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Workout, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_workout_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.titleTV.text = item.name
        holder.card.setOnClickListener { onItemClick?.invoke(Pair<Workout, View>(item, holder.card)) }
        Glide.with(holder.backgroundIV.context).load(item.image).into(holder.backgroundIV)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTV : TextView = view.card_text
        val backgroundIV : ImageView = view.card_image
        val card : CardView = view.workout_card
    }
}



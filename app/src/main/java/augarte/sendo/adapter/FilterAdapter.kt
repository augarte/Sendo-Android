package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.ExerciseType
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(private val items: ArrayList<ExerciseType>): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var favSelected: Boolean = false
    private var selectedList = ArrayList<ExerciseType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_filter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.chip.text = "FAV"
            holder.chip.setChipIconResource(R.drawable.ic_star_filled)
            holder.chip.setOnClickListener {
                favSelected = holder.chip.isSelected
            }
        } else {
            val item = items[position - 1]
            holder.chip.text = item.name
            holder.chip.setOnClickListener {
                if (holder.chip.isSelected) {
                    selectedList.add(item)
                } else {
                    selectedList.remove(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    fun getIsFavselected():Boolean{
        return favSelected
    }

    fun getSelectedTypeList():ArrayList<ExerciseType>{
        return selectedList
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chip : Chip = view.chip
    }
}
package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.dataModel.Measurement
import augarte.sendo.utils.Utils
import kotlinx.android.synthetic.main.item_measurement.view.*

class MeasurementAdapter(private val items : ArrayList<Measurement>) : RecyclerView.Adapter<MeasurementAdapter.ViewHolder>() {

    var onItemClick: ((Pair<Measurement, View>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_measurement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTV.text = item.value.toString()
        holder.date.text = Utils.dateToString(item.date)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleTV: TextView = view.value
        val date: TextView = view.date
    }
}



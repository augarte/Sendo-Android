package augarte.sendo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.dataModel.Measurement
import augarte.sendo.database.DatabaseConstants
import augarte.sendo.fragment.AddMeasureDialogFragment
import augarte.sendo.fragment.MeasurementsFragment
import augarte.sendo.fragment.ModifyMeasureDialogFragment
import augarte.sendo.utils.Utils
import augarte.sendo.utils.toStringFormat
import kotlinx.android.synthetic.main.fragment_measurements.*
import kotlinx.android.synthetic.main.item_measurement.view.*

class MeasurementAdapter(private val items : ArrayList<Measurement>) : RecyclerView.Adapter<MeasurementAdapter.ViewHolder>() {

    var onReload: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_measurement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTV.text = item.value?.toStringFormat
        holder.date.text = Utils.dateToString(item.date)

        holder.itemView.setOnClickListener {
            val listener = object: MeasurementsFragment.MeasureEditListener{
                override fun onDelete(measurement: Measurement) {
                    MainActivity.dbHandler.removeFromTable(DatabaseConstants.TABLE_MEASUREMENT, measurement.id!!)
                    onReload?.invoke()
                }

                override fun onModify(measurement: Measurement) {
                    MainActivity.dbHandler.updateMeasurementValue(measurement)
                    onReload?.invoke()
                }
            }
            val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            val modifyMeasureDialog = ModifyMeasureDialogFragment(item, listener)
            modifyMeasureDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
            modifyMeasureDialog.show(manager, "modifyMeasurement")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleTV: TextView = view.value
        val date: TextView = view.date
    }
}



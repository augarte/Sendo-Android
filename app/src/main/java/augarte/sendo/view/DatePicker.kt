package augarte.sendo.view

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.LinearLayout
import augarte.sendo.R
import augarte.sendo.utils.Utils
import kotlinx.android.synthetic.main.item_date_picker.view.*
import java.util.*
import java.text.SimpleDateFormat

class DatePicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), DatePickerDialog.OnDateSetListener{

    private var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    private val today = Calendar.getInstance()
    private val c = Calendar.getInstance()
    private var year = c.get(Calendar.YEAR)
    private var month = c.get(Calendar.MONTH)
    private var day = c.get(Calendar.DAY_OF_MONTH)

    init {
        LayoutInflater.from(context).inflate(R.layout.item_date_picker, this, true)
        orientation = HORIZONTAL

        date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, this, year, month, day)
            datePickerDialog.show()
        }

        previous.setOnClickListener{
            c.add(Calendar.DATE, -1)
            setDate()
        }

        next.setOnClickListener{
            c.add(Calendar.DATE, +1)
            setDate()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        year = p1
        month = p2
        day = p3

        c.time = Utils.partseDatePickerValues(year, month, day)

        setDate()
    }

    private fun setDate(){
        when {
            today.get(Calendar.DATE) == c.get(Calendar.DATE) -> date.text = "Today"
            today.get(Calendar.DATE) - c.get(Calendar.DATE) == 1 -> date.text = "Yesterday"
            c.get(Calendar.DATE) - today.get(Calendar.DATE) == 1 -> date.text = "Tomorrow"
            else -> date.text = dateFormat.format(c.time)
        }
    }
}



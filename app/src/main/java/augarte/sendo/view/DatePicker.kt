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

class DatePicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), DatePickerDialog.OnDateSetListener{

    private val create = Calendar.getInstance()
    private val c = Calendar.getInstance()
    private var year = c.get(Calendar.YEAR)
    private var month = c.get(Calendar.MONTH)+1
    private var day = c.get(Calendar.DAY_OF_MONTH)

    init {
        LayoutInflater.from(context).inflate(R.layout.item_date_picker, this, true)
        orientation = HORIZONTAL

        date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, this, year, month-1, day)
            datePickerDialog.datePicker.minDate = create.time.time
            datePickerDialog.show()
        }

        previous.setOnClickListener{
            if (create.get(Calendar.WEEK_OF_YEAR) < c.get(Calendar.WEEK_OF_YEAR)) {
                c.add(Calendar.WEEK_OF_YEAR, -1)
                setDate()
            }
        }

        next.setOnClickListener{
            c.add(Calendar.WEEK_OF_YEAR, +1)
            setDate()
        }
    }

    fun setUp(d: Date) {
        create.time = d

        setDate()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        year = p1
        month = p2+1
        day = p3

        c.time = Utils.partseDatePickerValues(year, month, day)

        setDate()
    }

    private fun setDate(){
        val weekNumber = "WEEK " + (c.get(Calendar.WEEK_OF_YEAR) - create.get(Calendar.WEEK_OF_YEAR) + 1)
        date.text = weekNumber
        if (create.get(Calendar.WEEK_OF_YEAR) < c.get(Calendar.WEEK_OF_YEAR)) previous.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_left))
        else if (create.get(Calendar.WEEK_OF_YEAR) == c.get(Calendar.WEEK_OF_YEAR)) previous.setImageDrawable(null)
    }
}



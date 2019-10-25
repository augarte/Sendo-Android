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
    private var weekNumber = 1
    private var listener: DatePickerListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.item_date_picker, this, true)
        orientation = HORIZONTAL

        date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, this, year, month-1, day)
            datePickerDialog.datePicker.minDate = create.time.time
            datePickerDialog.show()
        }

        previous.setOnClickListener{
            if (weekNumber > 1) {
                c.add(Calendar.WEEK_OF_YEAR, -1)
                year = c.get(Calendar.YEAR)
                month = c.get(Calendar.MONTH) + 1
                day = c.get(Calendar.DAY_OF_MONTH)
                setDate()
            }
        }

        next.setOnClickListener{
            c.add(Calendar.WEEK_OF_YEAR, +1)
            year = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH) + 1
            day = c.get(Calendar.DAY_OF_MONTH)
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

        c.time = Utils.parseDatePickerValues(year, month, day)

        setDate()
    }

    private fun setDate(){
        if (c.get(Calendar.YEAR) == create.get(Calendar.YEAR)) {
            weekNumber = ((c.get(Calendar.WEEK_OF_YEAR) - create.get(Calendar.WEEK_OF_YEAR) + 1))
        } else {
            val calAux = Calendar.getInstance()
            calAux.time = create.time
            for (i in 0 until c.get(Calendar.YEAR)-create.get(Calendar.YEAR)) {
                when {
                    calAux.get(Calendar.YEAR) == create.get(Calendar.YEAR) -> weekNumber = calAux.getActualMaximum(Calendar.WEEK_OF_YEAR) - create.get(Calendar.WEEK_OF_YEAR)
                    calAux.get(Calendar.YEAR) != c.get(Calendar.YEAR) -> weekNumber += calAux.getActualMaximum(Calendar.WEEK_OF_YEAR)
                }
                calAux.add(Calendar.YEAR, 1)
            }
            weekNumber += c.get(Calendar.WEEK_OF_YEAR) + 1
        }
        listener?.onDateWeekChanger(weekNumber)
        val weekString = context.getString(R.string.sendo_week_num, weekNumber)
        date.text = weekString
        if (weekNumber > 1) previous.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_left))
        else previous.setImageDrawable(null)
    }

    fun getWeek(): Int{
        return weekNumber
    }

    fun setListener(listener: DatePickerListener){
        this.listener = listener
    }

    interface DatePickerListener{
        fun onDateWeekChanger(week: Int)
    }
}



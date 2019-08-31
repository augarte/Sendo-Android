package augarte.sendo.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import augarte.sendo.R
import kotlinx.android.synthetic.main.item_number_picker.view.*

class CustomNumberPicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr){

    var maxVal: Int = 10
    var minVal: Int = 0
    var value: Int = 5
    var listener: ((oldVal: Int, newVal: Int) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.item_number_picker, this, true)
        orientation = HORIZONTAL

        button_up.setOnClickListener{
            if (value<maxVal) {
                val oldVal = value
                value++
                picker_value.text = ""+value
                listener?.invoke(oldVal, value)
            }
        }

        button_down.setOnClickListener{
            if (value>minVal) {
                val oldVal = value
                value--
                picker_value.text = ""+value
                listener?.invoke(oldVal, value)
            }
        }
    }
}
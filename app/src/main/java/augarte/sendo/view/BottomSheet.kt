package augarte.sendo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import augarte.sendo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.item_bottomsheet.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class BottomSheet @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CoordinatorLayout(context, attrs, defStyleAttr){

    private val bottomSheetBehaviour: BottomSheetBehavior<View>

    init {
        LayoutInflater.from(context).inflate(R.layout.item_bottomsheet, this, true)

        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
        setBottomSheetListener(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetLayout.setBackgroundResource(R.drawable.bottomsheet_border)
                    close.visibility = View.GONE
                }

                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED")
                    BottomSheetBehavior.STATE_DRAGGING -> Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING")
                    BottomSheetBehavior.STATE_EXPANDED ->{
                        bottomSheetLayout.setBackgroundResource(R.drawable.bottomsheet_full)
                        close.visibility  = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN")
                    BottomSheetBehavior.STATE_SETTLING -> Log.e("Bottom Sheet Behaviour", "STATE_SETTLING")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> Log.e("Bottom Sheet Behaviour", "STATE_SETTLING")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomsheet_background.alpha = slideOffset
            }
        })

        close.setOnClickListener{
            setState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    private fun setBottomSheetListener(listener: BottomSheetBehavior.BottomSheetCallback){
        bottomSheetBehaviour.bottomSheetCallback = listener
    }

    fun setState(state: Int){
        bottomSheetBehaviour.state = state
    }

    fun getState(): Int {
        return bottomSheetBehaviour.state
    }

    fun setFragment(fragment: Fragment){
        val fm = (context as AppCompatActivity).supportFragmentManager
        fm.beginTransaction()
                .replace(R.id.frame, fragment, fragment.tag)
                .addToBackStack(null)
                .commit()
        fm.executePendingTransactions()

        setState(BottomSheetBehavior.STATE_COLLAPSED)
    }
}
package augarte.sendo.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import augarte.sendo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.item_bottomsheet.view.*

class BottomSheet @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CoordinatorLayout(context, attrs, defStyleAttr){

    private val bottomSheetBehaviour: BottomSheetBehavior<View>
    var fullScreen: Boolean = true

    init {
        LayoutInflater.from(context).inflate(R.layout.item_bottomsheet, this, true)

        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehaviour.isFitToContents = true
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
        setBottomSheetListener(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetLayout.setBackgroundResource(R.drawable.bottomsheet_border)
                    close.visibility = View.GONE
                }

                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> coordinator.isClickable = false
                    BottomSheetBehavior.STATE_DRAGGING -> coordinator.isClickable = false
                    BottomSheetBehavior.STATE_EXPANDED ->{
                        coordinator.setOnClickListener { bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN }
                        if (fullScreen) bottomSheetLayout.setBackgroundResource(R.drawable.bottomsheet_full)
                        close.visibility  = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> coordinator.isClickable = false
                    BottomSheetBehavior.STATE_SETTLING -> coordinator.isClickable = false
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> coordinator.setOnClickListener { bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //bottomsheet_background.alpha = (slideOffset*0.75f)
            }
        })

        close.setOnClickListener{
            setState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    fun setCloseColor(color: Int){
        ImageViewCompat.setImageTintList(close, ColorStateList.valueOf(color))
    }

    private fun setBottomSheetListener(listener: BottomSheetBehavior.BottomSheetCallback){
        bottomSheetBehaviour.addBottomSheetCallback(listener)
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

        setState(BottomSheetBehavior.STATE_EXPANDED)
    }
}
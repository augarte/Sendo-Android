package augarte.sendo.activity

import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.dataModel.Day
import kotlinx.android.synthetic.main.activity_create_workout.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import android.view.animation.AnimationUtils.loadAnimation
import augarte.sendo.R
import augarte.sendo.fragment.ExerciseChooserFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CreateWorkoutActivity : AppCompatActivity() {

    val initialDayNum = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_workout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var dayList : ArrayList<Day> = ArrayList()
        for (i in 0 until initialDayNum) {
            var day = Day()
            day.name = "DAY " + (i+1)
            dayList.add(day)
        }

        val lManager = LinearLayoutManager(applicationContext)
        val workoutAdapter = CreateWorkoutAdapter(dayList, day_rv)
        val animator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }
        day_rv.apply {
            itemAnimator = animator
            layoutManager = lManager
            adapter = workoutAdapter
        }

        number_picker.minVal = 1
        number_picker.maxVal = 7
        number_picker.value = initialDayNum
        number_picker.listener = {oldVal, newVal ->
            if (newVal - oldVal > 0) {
                val d = Day()
                d.name = "DAY $newVal"
                dayList.add(oldVal, d)
                workoutAdapter.newItem = true
                workoutAdapter.notifyItemInserted(oldVal)
            }
            else if (newVal - oldVal < 0) {
                dayList.removeAt(newVal)
                workoutAdapter.deleteItem(newVal)
                workoutAdapter.notifyItemRemoved(newVal)
            }
        }

        val shake = loadAnimation(this, R.anim.hovering)
        create_workout_button.startAnimation(shake)

        create_workout_button.setOnClickListener{
            bottomsheet.setBottomSheetListener(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED")
                        BottomSheetBehavior.STATE_DRAGGING -> Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING")
                        BottomSheetBehavior.STATE_EXPANDED ->{

                        }
                        BottomSheetBehavior.STATE_HIDDEN -> Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN")
                        BottomSheetBehavior.STATE_SETTLING -> Log.e("Bottom Sheet Behaviour", "STATE_SETTLING")
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> Log.e("Bottom Sheet Behaviour", "STATE_SETTLING")
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
            bottomsheet.setFragment(ExerciseChooserFragment())
        }
    }
}
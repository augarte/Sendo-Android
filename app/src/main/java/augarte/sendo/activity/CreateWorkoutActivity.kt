package augarte.sendo.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import augarte.sendo.R
import kotlinx.android.synthetic.main.app_bar_main.*
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.dataModel.Day
import kotlinx.android.synthetic.main.activity_create_workout.*
import android.view.animation.Animation.AnimationListener
import android.view.animation.Animation


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

        val workoutAdapter = CreateWorkoutAdapter(dayList)
        day_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = workoutAdapter
        }

        number_picker.value = initialDayNum
        number_picker.setOnClickListener { Log.d("tag", "Click on current value") }
        number_picker.setOnValueChangedListener { picker, oldVal, newVal ->
            var changes = newVal - oldVal
            var negative = false
            if (changes < 0) {
                negative = true
                changes *= -1
            }
            for (i in 0 until changes) {
                if (negative) {
                    //dayList.removeAt(pos)
                    //workoutAdapter.notifyItemChanged(pos)
                        val anim = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
                        if (!anim.hasStarted() || anim.hasEnded()) {
                            val pos = dayList.size - 1
                            anim.duration = 200
                            day_rv.findViewHolderForAdapterPosition(pos)?.itemView?.startAnimation(anim)

                            /*anim.setAnimationListener(object : AnimationListener {
                                override fun onAnimationStart(animation: Animation) {

                                }

                                override fun onAnimationEnd(animation: Animation) {
                                    dayList.removeAt(pos)
                                    workoutAdapter.notifyDataSetChanged()
                                }

                                override fun onAnimationRepeat(animation: Animation) {

                                }
                            })*/

                            Handler().postDelayed({
                                dayList.removeAt(pos)
                                workoutAdapter.notifyDataSetChanged()
                            }, anim.duration)
                        }
                }
                else {
                    val pos = dayList.size
                    val d = Day()
                    d.name = "DAY " + (pos+1)
                    dayList.add(d)
                    workoutAdapter.notifyItemChanged(pos)
                }
            }

        }
    }
}
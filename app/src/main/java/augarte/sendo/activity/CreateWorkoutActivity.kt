package augarte.sendo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.dataModel.Day
import kotlinx.android.synthetic.main.activity_create_workout.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import android.view.animation.AnimationUtils.loadAnimation
import augarte.sendo.R


class CreateWorkoutActivity : AppCompatActivity() {

    val initialDayNum = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(augarte.sendo.R.layout.activity_create_workout)

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

        number_picker.value = initialDayNum
        number_picker.setOnClickListener { Log.d("tag", "Click on current value") }
        number_picker.setOnValueChangedListener { _, oldVal, newVal ->
            if (newVal - oldVal > 0) {
                val d = Day()
                d.name = "DAY $newVal"
                //dayList.add(oldVal, d)
                day_rv.post{workoutAdapter.addItem(oldVal, d)}
            }
            else if (newVal - oldVal < 0) {
                //dayList.removeAt(newVal)
                day_rv.post{workoutAdapter.deleteItem(newVal)}
            }
        }

        val shake = loadAnimation(this, R.anim.hovering)
        create_workout_button.startAnimation(shake)

    }
}
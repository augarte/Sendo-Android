package augarte.sendo.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.adapter.WorkoutDayAdapter
import augarte.sendo.dataModel.Workout
import augarte.sendo.database.SelectTransactions
import augarte.sendo.view.DatePicker
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_workout.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.item_workout_card.*

class WorkoutActivity : BaseActivity() {

    private lateinit var workout: Workout
    private lateinit var workoutDayAdapter: WorkoutDayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        workout = if (savedInstanceState == null) {
            intent.extras?.getParcelable("workout")!!
        } else {
            savedInstanceState.getParcelable("workout") as Workout
        }

        workout.image = MainActivity.dbHandler.getWorkoutImage(workout.id!!)

        setSupportActionBar(toolbar)
        supportActionBar?.title = workout.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        card_text.text = workout.name
        Glide.with(card_image.context).load(workout.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(card_image)
        workout_card.isClickable = false

        datePicker.setUp(workout.createDate!!)
        datePicker.setListener(object: DatePicker.DatePickerListener{
            override fun onDateWeekChanger(week: Int) {
                workoutDayAdapter.setWeekNumber(week)
            }
        })

        setAdapter()
    }

    private fun setAdapter(){
        workoutDayAdapter = WorkoutDayAdapter(workout.dayList)
        workoutDayAdapter.setWeekNumber(datePicker.getWeek())
        workoutDayAdapter.onItemClick = { pair ->
            val intent = Intent(this, WorkoutDayActivity::class.java)
            intent.putExtra("day", pair.first)
            intent.putExtra("week", datePicker.getWeek())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair.second, "dayCard")
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
            }
        }

        day_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = workoutDayAdapter
        }
    }

    override fun onRestart() {
        super.onRestart()
        val workoutList = MainActivity.dbHandler.getWorkouts(SelectTransactions.SELECT_WORKOUT_BY_ID, arrayOf(workout.id.toString()))
        if(workoutList.isNotEmpty()) workout =  workoutList.first()
        else onBackPressed()
        setAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu?.findItem(R.id.delete)?.isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.delete -> {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle(getString(R.string.sendo_alert_delete_workout_title))
                dialog.setMessage(getString(R.string.sendo_alert_delete_workout_message))
                dialog.setPositiveButton(getText(R.string.sendo_accept)) { _, _ ->
                    MainActivity.dbHandler.deleteWorkoutById(workout.id!!)
                    startActivity(Intent(this, MainActivity::class.java))
                }
                dialog.setNegativeButton(getString(R.string.sendo_cancel)) { _, _ -> }
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
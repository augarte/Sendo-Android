package augarte.sendo.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.app_bar_main.*
import augarte.sendo.R
import augarte.sendo.adapter.WorkoutDayAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_workout.datePicker
import kotlinx.android.synthetic.main.activity_workout.day_list
import kotlinx.android.synthetic.main.item_workout_card.*


class WorkoutActivity : AppCompatActivity() {

    private lateinit var workout: Workout

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
        Glide.with(card_image.context).load(workout.image).into(card_image)
        workout_card.isClickable = false

        datePicker.setUp(workout.createDate!!)

        val workoutDayAdapter = WorkoutDayAdapter(workout.dayList)
        workoutDayAdapter.onItemClick = { pair ->
            val intent = Intent(this, AddProgressActivity::class.java)
            intent.putExtra("day", pair.first)
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
                dialog.setTitle("Delete workout")
                dialog.setMessage("Are you sure you want to delete this workout?")
                dialog.setPositiveButton("Accept") { _, _ ->
                    MainActivity.dbHandler.deleteWorkoutById(workout.id!!)
                    startActivity(Intent(this, MainActivity::class.java))
                }
                dialog.setNegativeButton("Cancel") { _, _ -> }
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
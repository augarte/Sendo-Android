package augarte.sendo.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import augarte.sendo.R
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.app_bar_main.*
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

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        card_text.text = workout.name
        //card_image.setImageBitmap(workout.image)
        workout_card.isClickable = false


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
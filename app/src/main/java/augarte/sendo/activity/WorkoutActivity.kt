package augarte.sendo.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.item_workout_card.*
import kotlinx.android.synthetic.main.item_workout_card.workout_card
import augarte.sendo.R
import augarte.sendo.adapter.WorkoutPagerAdapter
import augarte.sendo.fragment.WorkoutPagerProgressFragment
import augarte.sendo.fragment.WorkoutPagerTimerFragment
import augarte.sendo.fragment.WorkoutPagerWorkoutFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_workout.*


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
        supportActionBar?.title = workout.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pageAdapter = WorkoutPagerAdapter(supportFragmentManager)
        pageAdapter.addFragment(WorkoutPagerWorkoutFragment())
        pageAdapter.addFragment(WorkoutPagerProgressFragment())
        pageAdapter.addFragment(WorkoutPagerTimerFragment())
        viewPager.adapter = pageAdapter

        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.workout ->{
                    viewPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.progress ->{
                    viewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.timer ->{
                    viewPager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                } else -> viewPager.currentItem = 1
            }
            false
        })
        bottomNavigation.selectedItemId = R.id.workout

        /*card_text.text = workout.name
        //card_image.setImageBitmap(workout.image)
        workout_card.isClickable = false*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu!!.findItem(R.id.delete).isVisible = true
        menu.findItem(R.id.delete).icon.let {
            DrawableCompat.setTint(ContextCompat.getDrawable(this, R.drawable.ic_delete)!!, ContextCompat.getColor(this, R.color.white))
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.delete -> {
                MainActivity.dbHandler!!.deleteWorkoutById(workout.id!!)
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
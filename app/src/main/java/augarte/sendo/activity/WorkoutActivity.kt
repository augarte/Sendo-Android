package augarte.sendo.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.app_bar_main.*
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
        ActivityCompat.postponeEnterTransition(this)
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
        pageAdapter.addFragment(WorkoutPagerWorkoutFragment(workout))
        pageAdapter.addFragment(WorkoutPagerProgressFragment())
        pageAdapter.addFragment(WorkoutPagerTimerFragment())
        viewPager.adapter = pageAdapter

        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigation.menu.getItem(position).isChecked = true
            }

        })

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
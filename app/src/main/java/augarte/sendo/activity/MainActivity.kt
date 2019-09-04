package augarte.sendo.activity

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import augarte.sendo.database.DatabaseHandler
import augarte.sendo.fragment.ExerciseListFragment
import augarte.sendo.fragment.HomeFragment
import augarte.sendo.fragment.MeasurementsFragment
import augarte.sendo.fragment.SettingsFragment
import augarte.sendo.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var dbHandler: DatabaseHandler? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //init db
        dbHandler = DatabaseHandler(this)
        //MainActivity.dbHandler?.deleteAllTables()

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment.newInstance(), "Home")
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (menuItem.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
            }

            R.id.nav_measurement -> {
                fragment = MeasurementsFragment()
            }

            R.id.nav_exercise_list -> {
                fragment = ExerciseListFragment()
            }

            R.id.nav_settings -> {
                fragment = SettingsFragment()
            }
        }

        if (fragment != null)  {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, fragment)
            transaction.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showTapTarget() {
        TapTargetView.showFor(this,
            TapTarget.forView(speedDial, "This is a target", "We have the best targets, believe me")
                    // All options below are optional
                    .outerCircleColor(R.color.colorPrimaryLight)      // Specify a color for the outer circle
                    .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                    .targetCircleColor(R.color.white)   // Specify a color for the target circle
                    .titleTextSize(28)                  // Specify the size (in sp) of the title text
                    .titleTextColor(R.color.primaryText)      // Specify the color of the title text
                    .descriptionTextSize(18)            // Specify the size (in sp) of the description text
                    .descriptionTextColor(R.color.black)  // Specify the color of the description text
                    .textColor(R.color.primaryText)            // Specify a color for both the title and description text
                    //.textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                    .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                    .drawShadow(true)                   // Whether to draw a drop shadow or not
                    .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                    .tintTarget(false)                   // Whether to tint the target view's color
                    .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                    //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                    .targetRadius(60),                  // Specify the target radius (in dp)
            object: TapTargetView.Listener(){         // The listener can listen for regular clicks, long clicks or cancels
            }
        )
    }
}